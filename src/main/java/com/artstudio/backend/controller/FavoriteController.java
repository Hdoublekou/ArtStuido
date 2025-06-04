package com.artstudio.backend.controller;

import com.artstudio.backend.model.Favorite;
import com.artstudio.backend.repository.FavoriteRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;

    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    // 查询用户所有收藏
    @GetMapping("/user/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    // 添加收藏
    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        // 可以加上判断避免重复收藏
        if (favoriteRepository.findByUserIdAndWorkId(favorite.getUserId(), favorite.getWorkId()) == null) {
            return favoriteRepository.save(favorite);
        }
        return favorite;
    }

    @DeleteMapping("/delete/{userId}/{workId}")
    @Transactional
    public void deleteFavorite(@PathVariable Long userId, @PathVariable Long workId) {
        favoriteRepository.deleteByUserIdAndWorkId(userId, workId);
    }
}
