package vn.duyta.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.duyta.orderservice.dto.response.OrderItemResponse;
import vn.duyta.orderservice.dto.response.OrderResponse;
import vn.duyta.orderservice.model.Order;
import vn.duyta.orderservice.model.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "keycloakUserId", target = "keycloakUserId")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    OrderResponse toOrderResponse(Order order);

    OrderItemResponse toOrderItemResponse(OrderItem orderItem);

    List<OrderResponse> toOrderResponseList(List<Order> orders);
}
