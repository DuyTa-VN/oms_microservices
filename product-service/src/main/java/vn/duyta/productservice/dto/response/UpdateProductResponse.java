package vn.duyta.productservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
public class UpdateProductResponse {
    private long id;
    private String name;
    private String description;
    private double price;
    private String sku;
    private String brand;
    private String category;
    private long quantity;
    private List<String> image;
    private LocalDateTime updatedAt;
}
