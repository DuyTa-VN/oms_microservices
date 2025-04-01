package vn.duyta.orderservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
