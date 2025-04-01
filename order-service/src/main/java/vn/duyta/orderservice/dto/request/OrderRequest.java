package vn.duyta.orderservice.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;
}
