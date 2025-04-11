package com.artstudio.backend.dto;

import com.artstudio.backend.model.User;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
	public UserDto(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

//    public UserDto(User user) {
//        this.name = user.getName();
//        this.email = user.getEmail();
//        this.password = user.getPassword();
    
    
}
