package vn.duyta.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import vn.duyta.orderservice.dto.request.OrderRequest;
import vn.duyta.orderservice.dto.response.OrderResponse;
import vn.duyta.orderservice.mapper.OrderMapper;
import vn.duyta.orderservice.model.Order;
import vn.duyta.orderservice.model.OrderItem;
import vn.duyta.orderservice.repository.OrderRepository;
import vn.duyta.orderservice.repository.ProductClient;
import vn.duyta.orderservice.util.constant.OrderStatus;
import vn.duyta.orderservice.util.error.IdInvalidException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    // create order
    public OrderResponse createOrder(OrderRequest request){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String keycloakUserId = jwt.getSubject();



        List<OrderItem> items = request.getItems().stream()
                .map(itemRequest -> {
                    ProductClient.ProductResponse product = productClient.getProduct(itemRequest.getProductId());
                    System.out.println("Product API Response: " + product);

                    if (product == null) {
                        throw new IllegalArgumentException("Product with id = " + itemRequest.getProductId() + " not found");
                    }

                    BigDecimal price = product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO;
                    return OrderItem.builder()
                            .productId(product.getId())
                            .quantity(itemRequest.getQuantity())
                            .price(price)
                            .productName(product.getName())
                            .build();
                })
                .toList();

        BigDecimal totalPrice = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .keycloakUserId(keycloakUserId)
                .status(OrderStatus.PENDING)
                .items(items)
                .totalPrice(totalPrice)
                .build();

        return orderMapper.toOrderResponse(this.orderRepository.save(order));

    }

}
