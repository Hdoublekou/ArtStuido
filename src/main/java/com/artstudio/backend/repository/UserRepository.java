package com.artstudio.backend.repository;

import com.artstudio.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmailAndDeletedFalse(String email);

    Optional<User> findByIdAndDeletedFalse(Long id);
    
    List<User> findByRole(String role);

    Optional<User> findByName(String name);
    
    List<User> findByDeletedFalse();
    
    List<User> findByRoleAndDeletedFalse(String role);

}
