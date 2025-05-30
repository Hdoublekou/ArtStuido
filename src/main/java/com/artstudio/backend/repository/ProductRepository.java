package com.artstudio.backend.repository;

import com.artstudio.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 商品数据访问层
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopId(Long shopId);

    List<Product> findByIsActiveTrueOrderByCreatedAtDesc();

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
