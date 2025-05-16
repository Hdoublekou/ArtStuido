package com.artstudio.backend.dto;

import com.artstudio.backend.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // 自动生成 getter、setter、toString、equals、hashCode
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String avatarUrl;
    
    // 构造函数（用于手动赋值）
    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }

    // 从实体类 User 转换而来
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.avatarUrl = user.getAvatarUrl();
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
