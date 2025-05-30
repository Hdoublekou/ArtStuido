package com.artstudio.backend.dto;

import com.artstudio.backend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String avatarUrl;
    private String nickname; // 新增昵称字段
    private String role;     // 新增角色字段
    private Boolean deleted; // 可选：标记逻辑删除
    private String createdAt; // 可选：注册时间（String或LocalDateTime都可）

    // 常规构造函数
    public UserDto(Long id, String name, String email, String role, String nickname, String avatarUrl, Boolean deleted, String createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    // 从实体类 User 转换
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.avatarUrl = user.getAvatarUrl();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.deleted = user.isDeleted();
        this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
    }
}
