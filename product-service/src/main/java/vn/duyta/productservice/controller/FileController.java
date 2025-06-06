package vn.duyta.productservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.duyta.productservice.dto.response.UploadFileResponse;
import vn.duyta.productservice.service.FileService;
import vn.duyta.productservice.util.annotation.ApiMessage;
import vn.duyta.productservice.util.error.StorageException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Value("${duyta.upload-file.base-uri}")
    private String baseURI;

    @PostMapping("/files")
    @ApiMessage("Upload single file")
    public ResponseEntity<UploadFileResponse> upload(
            @RequestParam(name = "file", required = false)MultipartFile file,
            @RequestParam("folder") String folder
            ) throws URISyntaxException, IOException, StorageException {
        //Skip validate
        if (file == null || file.isEmpty()){
            throw new StorageException("File is empty. Please upload a file");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));

        if (!isValid){
            throw new StorageException("Invalid file extensions. Only allows " + allowedExtensions.toString());
        }

        //create a directory if not exist
        this.fileService.createDirectory(baseURI + folder);
        //store file
        String uploadFile = this.fileService.store(file, folder);
        UploadFileResponse res = new UploadFileResponse(uploadFile, LocalDateTime.now());
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/files")
    @ApiMessage("Download a file")
    public ResponseEntity<Resource> download(
            @RequestParam(name = "fileName", required = false) String fileName,
            @RequestParam(name = "folder", required = false) String folder)
            throws StorageException, URISyntaxException, FileNotFoundException
    {
        if (fileName == null || folder == null) {
            throw new StorageException("Missing required params : (fileName or folder) in query params.");
        }
        //check file exist (and not a directory)
        long fileLength = this.fileService.getFileLength(fileName, folder);
        if (fileLength == 0){
            throw new StorageException("File with name = " + fileName + " not found.");
        }

        //download a file
        InputStreamResource resource = this.fileService.getResource(fileName, folder);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(fileLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
