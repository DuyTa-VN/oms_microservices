package vn.duyta.productservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.service.ProductService;
import vn.duyta.productservice.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ApiMessage("Create a product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product reqProduct) {
        Product resProduct = this.productService.handleCreateProduct(reqProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(resProduct);
    }
}
