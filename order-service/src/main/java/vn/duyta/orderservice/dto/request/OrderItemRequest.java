package vn.duyta.orderservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
