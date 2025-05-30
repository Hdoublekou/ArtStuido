package com.artstudio.backend.service;

import com.artstudio.backend.dto.UserDto;
import com.artstudio.backend.model.User;
import com.artstudio.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 修改密码
    public void updatePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));

        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("現在のパスワードが正しくありません");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    // 注册
    public UserDto registerUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole() != null ? userDto.getRole() : "user"); // 新增角色字段
        user.setNickname(userDto.getNickname()); // 新增昵称字段
        User saved = userRepository.save(user);
        return convertToDto(saved);
    }

    // 获取用户信息 by email
    public UserDto getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.map(this::convertToDto).orElse(null);
    }

    // User -> UserDto
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setNickname(user.getNickname());
        dto.setAvatarUrl(user.getAvatarUrl());
        return dto;
    }

    // 更新用户信息
    public UserDto updateUser(Long id, UserDto updatedData) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setEmail(updatedData.getEmail());
            user.setPassword(updatedData.getPassword());
            user.setRole(updatedData.getRole());
            user.setNickname(updatedData.getNickname());
            user.setName(updatedData.getName());
            User saved = userRepository.save(user);
            return convertToDto(saved);
        } else {
            throw new RuntimeException("未找到用户");
        }
    }

    // 伦理删除
    public void deleteUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setDeleted(true);
            userRepository.save(user);
        } else {
            throw new RuntimeException("用户未找到");
        }
    }

    // 登录，排除已删除用户
    public UserDto loginUser(String email, String password) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new RuntimeException("メールまたはパスワードが違います"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("メールまたはパスワードが違います");
        }
        return convertToDto(user);
    }

    // 头像上传及路径保存
    public String saveAvatarFile(Long userId, MultipartFile file) {
        if (file.isEmpty()) throw new RuntimeException("ファイルが空です");
        try {
            Path uploadDir = Paths.get("uploads", "avatar").toAbsolutePath();
            Files.createDirectories(uploadDir);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
            Path filePath = uploadDir.resolve(filename);
            file.transferTo(filePath.toFile());
            String avatarUrl = "/uploads/avatar/" + filename;
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);
            return avatarUrl;
        } catch (IOException e) {
            throw new RuntimeException("アバターアップロード失敗", e);
        }
    }

    // ID查用户
    public UserDto getUserDtoById(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("ユーザーが見つかりません"));
        return convertToDto(user);
    }

    // 按角色查所有用户（多端管理后台等可用）
    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userRepository.findByRoleAndDeletedFalse(role);
        return users.stream().map(this::convertToDto).toList();
    }

    // 获取全部用户（仅管理员后台用）
    public List<UserDto> getAllActiveUsers() {
        List<User> users = userRepository.findByDeletedFalse();
        return users.stream().map(this::convertToDto).toList();
    }
    
 // 修改昵称
    public UserDto updateNickname(Long id, String nickname) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("用户未找到"));
        user.setNickname(nickname);
        userRepository.save(user);
        return convertToDto(user);
    }

}
