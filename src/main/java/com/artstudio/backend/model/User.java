package com.artstudio.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 登录用姓名
    private String name;

    // 头像URL
    @Column(name = "avatar_url")
    private String avatarUrl;

    // 邮箱（唯一）
    @Column(unique = true)
    private String email;

    // 密码
    private String password;

    // 逻辑删除标志
    @Column(nullable = false)
    private boolean deleted = false;

    // 角色字段：user/shop/admin
    @Column(nullable = false)
    private String role = "user";

    // 昵称
    private String nickname;

    // 注册时间
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 创建时自动赋值
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
