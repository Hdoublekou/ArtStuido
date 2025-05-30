package com.artstudio.backend.service;

import com.artstudio.backend.model.Product;
import com.artstudio.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// 商品业务服务
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 创建或修改商品
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // 查询所有上架商品
    public List<Product> listActive() {
        return productRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    // 根据ID查询
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    // 店主查看自己所有商品
    public List<Product> findByShopId(Long shopId) {
        return productRepository.findByShopId(shopId);
    }

    // 关键词搜索
    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}
