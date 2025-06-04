package com.artstudio.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 订单发货用DTO（可根据需求扩展字段）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipOrderRequest {
    private Long shipperId;       // 物流公司ID（或自定义编码/名称）
    private String shipperName;   // 物流公司名称
    private String shipperNo;     // 运单号
    private String receiverName;  // 收件人姓名
    private String address;       // 收件地址
    private String phone;         // 收件人电话
    private String remark;        // 发货备注（可选）

    // 你可以根据实际需求继续扩展字段，如：快递类型、发货时间等
}
