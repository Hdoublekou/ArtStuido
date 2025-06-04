package com.artstudio.backend.controller;

import com.artstudio.backend.model.Shop;
import com.artstudio.backend.repository.ShopRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    // 店铺新建
    @PostMapping
    public Shop create(@RequestBody Shop shop) {
        shop.setCreatedAt(LocalDateTime.now());
        return shopRepository.save(shop);
    }

    // 店铺编辑
    @PutMapping("/{id}")
    public Shop edit(@PathVariable Long id, @RequestBody Shop s) {
        Shop shop = shopRepository.findById(id).orElseThrow();
        shop.setName(s.getName());
        shop.setAddress(s.getAddress());
        shop.setPhone(s.getPhone());
        shop.setDescription(s.getDescription());
        return shopRepository.save(shop);
    }

    // 单用户店铺一览
    @GetMapping("/owner/{ownerId}")
    public List<Shop> byOwner(@PathVariable Long ownerId) {
        return shopRepository.findByOwnerId(ownerId);
    }

    // 单个店铺详情
    @GetMapping("/{id}")
    public Shop detail(@PathVariable Long id) {
        return shopRepository.findById(id).orElseThrow();
    }
}
