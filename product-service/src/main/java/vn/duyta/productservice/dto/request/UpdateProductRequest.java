package vn.duyta.productservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateProductRequest {
    private String name;
    private String description;

    @DecimalMin(value = "0", message = "Price must be than 0")
    private double price;

    private long quantity;
    private List<String> images;
}
