package com.artstudio.backend.controller;

import com.artstudio.backend.model.Product;
import com.artstudio.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    // 店主上新/编辑商品（JSON接口，可选）
    @PostMapping("/shop")
    public Product shopSave(@RequestBody Product product) {
        return productService.save(product);
    }

    // 店主查询自己商品
    @GetMapping("/shop/{shopId}")
    public List<Product> byShop(@PathVariable Long shopId) {
        return productService.findByShopId(shopId);
    }

    // 新建商品/图片上传接口
    @PostMapping("/upload")
    public Product uploadProduct(
        @RequestParam("shopId") Long shopId,
        @RequestParam("name") String name,
        @RequestParam("price") Double price,
        @RequestParam("stock") Integer stock,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        Product product = new Product();
        product.setShopId(shopId);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        // 如有 description 等字段可加
        return productService.saveWithImage(product, image);
    }

    // 编辑商品（只改非图片字段）
    @PutMapping("/{id}")
    public Product edit(@PathVariable Long id, @RequestBody Product p) {
        return productService.update(id, p);
    }

    // 删除商品
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // 商品上下架
    @PostMapping("/toggle-active/{id}")
    public Product toggleActive(@PathVariable Long id) {
        return productService.toggleActive(id);
    }

    // 商品活动开关
    @PostMapping("/toggle-event/{id}")
    public Product toggleEvent(@PathVariable Long id) {
        return productService.toggleEvent(id);
    }

    // 最近产品
    @GetMapping("/recent")
    public List<Product> getRecentProducts() {
        return productService.getRecentProducts();
    }
}
