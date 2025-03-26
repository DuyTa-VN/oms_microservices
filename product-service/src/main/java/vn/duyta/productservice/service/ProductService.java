package vn.duyta.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product handleCreateProduct(Product product){
        return this.productRepository.save(product);
    }

    public List<Product> fetchAllProducts(){
        return productRepository.findAll();
    }
}
