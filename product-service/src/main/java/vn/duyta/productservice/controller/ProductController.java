package vn.duyta.productservice.controller;

import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.request.UpdateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.ProductResponse;
import vn.duyta.productservice.dto.response.ResultPaginationDTO;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.service.FileService;
import vn.duyta.productservice.service.ProductService;
import vn.duyta.productservice.util.annotation.ApiMessage;
import vn.duyta.productservice.util.error.IdInvalidException;

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
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long id) throws IdInvalidException{

        return ResponseEntity.ok().body(this.productService.fetchProductById(id));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) throws IdInvalidException{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
