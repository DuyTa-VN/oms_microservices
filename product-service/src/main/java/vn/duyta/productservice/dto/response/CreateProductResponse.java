package vn.duyta.productservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class CreateProductResponse {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private String brand;
    private long quantity;
    private String category;
    private List<String> image;
    private LocalDateTime createdAt;
}
