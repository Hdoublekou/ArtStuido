package com.artstudio.backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    private Long userId;
    private Long workId;
    private BigDecimal totalPrice;
}
