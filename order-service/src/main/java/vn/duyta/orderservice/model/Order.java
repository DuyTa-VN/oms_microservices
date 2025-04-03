package vn.duyta.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import vn.duyta.orderservice.util.constant.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal totalPrice;

    private String keycloakUserId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    private LocalDateTime createdAt;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
