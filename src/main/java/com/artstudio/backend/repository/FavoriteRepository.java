package com.artstudio.backend.repository;

import com.artstudio.backend.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Favorite findByUserIdAndWorkId(Long userId, Long workId);
    void deleteByUserIdAndWorkId(Long userId, Long workId);
}
