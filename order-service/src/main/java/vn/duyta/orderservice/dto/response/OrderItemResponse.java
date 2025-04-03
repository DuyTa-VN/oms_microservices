package vn.duyta.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private long id;
    private Long productId;
    private String productName;
    private long quantity;
    private BigDecimal price;
}
