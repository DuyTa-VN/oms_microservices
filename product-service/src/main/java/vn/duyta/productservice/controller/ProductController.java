package vn.duyta.productservice.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.request.UpdateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.ResultPaginationDTO;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.service.FileService;
import vn.duyta.productservice.service.ProductService;
import vn.duyta.productservice.util.annotation.ApiMessage;
import vn.duyta.productservice.util.error.IdInvalidException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final FileService fileService;

    @PostMapping
    @ApiMessage("Create a product")
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.handleCreateProduct(request));
    }

    @PutMapping("/{id}")
    @ApiMessage("Update a product")
    public ResponseEntity<UpdateProductResponse> updateProduct(
            @Valid
            @PathVariable("id") long id,
            @RequestBody UpdateProductRequest request) throws IdInvalidException {
        return ResponseEntity.ok().body(this.productService.handleUpdateProduct(id, request));
    }

    @GetMapping("/search")
    @ApiMessage("Fetch all products")
    public ResponseEntity<ResultPaginationDTO> getAllProducts(
            @Filter Specification<Product> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(this.productService.handleGetAllProduct(spec, pageable));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch product by ID")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws IdInvalidException{
        Product fetchProduct = this.productService.fetchProductById(id);
        if (fetchProduct == null) {
            throw new IdInvalidException("Product with id = " + id + " not found");
        }
        return ResponseEntity.ok().body(fetchProduct);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) throws IdInvalidException{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
