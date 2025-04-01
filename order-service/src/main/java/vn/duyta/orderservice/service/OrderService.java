package vn.duyta.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.duyta.orderservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


}
