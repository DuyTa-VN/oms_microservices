package vn.duyta.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.duyta.orderservice.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
