package com.artstudio.backend.dto;

import lombok.Data;

@Data
public class CartUpdateRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
}
