package vn.duyta.orderservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private long id;
    private String keycloakUserId;
    private BigDecimal totalPrice;
    private String status;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;
}
