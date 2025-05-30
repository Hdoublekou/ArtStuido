package com.artstudio.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artstudio.backend.model.CartItem;
import com.artstudio.backend.repository.CartItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;

    public List<CartItem> getCart(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public CartItem addToCart(Long userId, Long productId, Integer quantity) {
        return cartItemRepository.findByUserIdAndProductId(userId, productId)
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> cartItemRepository.save(new CartItem(null, userId, productId, quantity)));
    }

    public void updateQuantity(Long userId, Long productId, Integer quantity) {
        cartItemRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    cartItemRepository.save(item);
                });
    }

    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }

    public void clearCart(Long userId) {
        cartItemRepository.findByUserId(userId).forEach(item -> cartItemRepository.delete(item));
    }
}
