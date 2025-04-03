package vn.duyta.orderservice.dto.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private String brand;
    private String category;
    private long quantity;
    private List<String> image;
}
