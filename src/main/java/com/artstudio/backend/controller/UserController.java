package com.artstudio.backend.controller; // ✅ 建议明确 package 名，和其他类一致

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.artstudio.backend.dto.LoginRequest;
import com.artstudio.backend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import com.artstudio.backend.service.UserService; // ✅ 确保导入路径完整且正确

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.registerUser(userDto);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginRequest loginRequest) {
        UserDto user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestParam String email) {
        UserDto user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserDto updated = userService.updateUser(id, userDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("已注销账户");
    }
}
