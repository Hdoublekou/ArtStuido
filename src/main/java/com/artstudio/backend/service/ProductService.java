package com.artstudio.backend.service;

import com.artstudio.backend.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getRecentProducts();
    Product save(Product product);
    List<Product> listActive();
    Optional<Product> findById(Long id);
    List<Product> findByShopId(Long shopId);
    List<Product> search(String keyword);
    Product update(Long id, Product product);
    void delete(Long id);
    Product toggleActive(Long id);
    Product toggleEvent(Long id);

    // 两个都要有，便于兼容
    Product saveWithImage(Product product, MultipartFile image);
    Product saveWithImage(Long shopId, String name, Double price, Integer stock, MultipartFile image);
}
