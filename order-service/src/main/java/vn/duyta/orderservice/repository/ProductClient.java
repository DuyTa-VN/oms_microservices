package vn.duyta.orderservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.duyta.orderservice.dto.client.ProductResponse;

@FeignClient(name = "product-service", url = "http://localhost:8082/product")
public interface ProductClient {
    @GetMapping("/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);
}
