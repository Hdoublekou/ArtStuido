package com.artstudio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;         // 商品名
    private String description;  // 商品描述
    private String imageUrl;     // 商品图片
    private Double price;        // 单价
    private Integer stock;       // 库存
    private Long shopId;         // 店主ID

    private Boolean isActive = true; // 是否上架
    private Boolean isEvent = false; // 是否为活动商品

    private LocalDateTime createdAt;

    // Lombok @Data 生成 getter/setter，但如果你有自定义逻辑可手写
    // 这里手动写 getter/setter，保证兼容 JPA 查询
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsEvent() {
        return isEvent;
    }
    public void setIsEvent(Boolean isEvent) {
        this.isEvent = isEvent;
    }
}
