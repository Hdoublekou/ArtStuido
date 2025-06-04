package com.artstudio.backend.repository;

import com.artstudio.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrueOrderByCreatedAtDesc();
    List<Product> findByIsEventTrue();
    List<Product> findTop10ByOrderByIdDesc();
    List<Product> findByShopId(Long shopId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
