package com.artstudio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;    // 下单用户ID
    private Long workId;    // 作品/课程ID

    @Column(nullable = false)
    private BigDecimal totalPrice; // 订单总价

    private String status;  // CREATED, PAID, CANCELLED

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;
    
    private Long shopId;
    private Long shipperId;               // 物流公司ID
    private String shippingStatus;        // 未发货/已发货/已完成等
    private LocalDateTime shippedAt;
}
