package vn.duyta.orderservice.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductClient {
    @GetMapping("/product/{id}")
    ProductResponse getProduct(@PathVariable("id") Long id);

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class ProductResponse {
        private Long id;
        private String name;
        private BigDecimal price;
    }
}
