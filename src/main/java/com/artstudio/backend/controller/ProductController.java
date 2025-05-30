package com.artstudio.backend.controller;

import com.artstudio.backend.model.Product;
import com.artstudio.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 商品相关接口（商品列表、商品详情、商品创建/编辑/删除）
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 全部上架商品列表
    @GetMapping
    public List<Product> list() {
        return productService.listActive();
    }

    // 关键词搜索
    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return productService.search(keyword);
    }

    // 商品详情
    @GetMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(() -> new RuntimeException("商品が見つかりません"));
    }

    // 店主上新/编辑商品
    @PostMapping("/shop")
    public Product shopSave(@RequestBody Product product) {
        return productService.save(product);
    }

    // 店主查询自己商品
    @GetMapping("/shop/{shopId}")
    public List<Product> byShop(@PathVariable Long shopId) {
        return productService.findByShopId(shopId);
    }
}
