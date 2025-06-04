package com.artstudio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;          // 用户ID

    private String recipient;     // 收件人
    private String postcode;      // 邮编
    private String prefecture;    // 都道府县
    private String city;          // 市区町村
    private String address;       // 详细地址
    private String phone;         // 电话
}
