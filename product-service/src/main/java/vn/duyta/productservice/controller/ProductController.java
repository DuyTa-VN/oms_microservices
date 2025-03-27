package vn.duyta.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.request.UpdateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.service.ProductService;
import vn.duyta.productservice.util.annotation.ApiMessage;
import vn.duyta.productservice.util.error.IdInvalidException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

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

    @GetMapping
    @ApiMessage("Fetch all products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(this.productService.fetchAllProducts());
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a product")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id) throws IdInvalidException{
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
