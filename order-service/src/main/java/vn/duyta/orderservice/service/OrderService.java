package vn.duyta.orderservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import vn.duyta.orderservice.dto.client.ProductResponse;
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
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    // create order
    public OrderResponse createOrder(OrderRequest request) throws IdInvalidException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String keycloakUserId = authentication.getName();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            try {
                ProductResponse product = productClient.getProductById(item.getProductId());
                if (item.getProductId() == null){
                    throw new IdInvalidException("Product ID is null");
                }
                if (product.getPrice() == null){
                    throw new IdInvalidException("Product price is null for product ID: " + item.getProductId());
                }
                OrderItem orderItem = OrderItem.builder()
                        .productId(product.getId())
                        .quantity(item.getQuantity())
                        .productName(product.getName())
                        .price(product.getPrice())
                        .build();
                orderItems.add(orderItem);
                totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            } catch (FeignException e) {
                log.error("Error fetching product with ID {}: {}", item.getProductId(), e.getMessage());
                throw new IdInvalidException("Product not found");
            }
        }

        Order order = Order.builder()
                .keycloakUserId(keycloakUserId)
                .items(orderItems)
                .totalPrice(totalPrice)
                .status(OrderStatus.PENDING)
                .build();
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }
}
