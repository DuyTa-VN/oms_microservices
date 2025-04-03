package vn.duyta.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.duyta.orderservice.dto.request.OrderRequest;
import vn.duyta.orderservice.dto.response.OrderResponse;
import vn.duyta.orderservice.service.OrderService;
import vn.duyta.orderservice.util.annotation.ApiMessage;
import vn.duyta.orderservice.util.error.IdInvalidException;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ApiMessage("Create an order")
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest orderRequest,
            @AuthenticationPrincipal Jwt jwt
            ) throws IdInvalidException {
        OrderResponse orderResponse = this.orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
