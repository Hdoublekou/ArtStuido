package com.artstudio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

// 商品表实体类，对应products表
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 商品名
    private String description; // 商品描述
    private String imageUrl; // 商品图片
    private Double price; // 单价
    private Integer stock; // 库存
    private Long shopId; // 店主ID
    private Boolean isEvent = false; // 是否为活动商品
    private Boolean isActive = true; // 是否上架
    private LocalDateTime createdAt = LocalDateTime.now();
}
