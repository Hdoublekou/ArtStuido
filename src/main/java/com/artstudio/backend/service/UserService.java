package com.artstudio.backend.service;

import com.artstudio.backend.dto.UserDto;
import com.artstudio.backend.model.User;
import com.artstudio.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    // 登录用方法
//    public UserDto loginUser(String email, String password) {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
//            return convertToDto(userOpt.get());
//        }
//        throw new RuntimeException("邮箱或密码错误");
//    }

    // 注册用方法
    public UserDto registerUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        User saved = userRepository.save(user);
        return convertToDto(saved);
    }

    // ✅ 获取用户信息用方法
    public UserDto getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(this::convertToDto).orElse(null);
    }

    // ✅ 用于统一转换 User -> UserDto
    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
    
    public UserDto updateUser(Long id, UserDto updatedData) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(updatedData.getEmail());
            user.setPassword(updatedData.getPassword());
            User saved = userRepository.save(user);
            return convertToDto(saved);
        } else {
            throw new RuntimeException("未找到用户");
        }
    }

    public void deleteUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setDeleted(true); // 伦理删除标记
            userRepository.save(user);
        } else {
            throw new RuntimeException("用户未找到");
        }
    }
    
    public UserDto loginUser(String email, String password) {
        // ✅ 加入 deleted = false 的条件
        User user = userRepository.findByEmailAndDeletedFalse(email)
            .orElseThrow(() -> new RuntimeException("邮箱或密码错误"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("邮箱或密码错误");
        }

        return new UserDto(user);
    }

}
