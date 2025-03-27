package vn.duyta.productservice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String description;
    private double price;
    private String sku;
    private String brand;
    private long quantity;
    private String category;
    private List<String> image;
}
