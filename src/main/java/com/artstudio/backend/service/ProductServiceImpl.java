package com.artstudio.backend.service;

import com.artstudio.backend.model.Product;
import com.artstudio.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getRecentProducts() {
        return productRepository.findTop10ByOrderByIdDesc();
    }

    @Override
    public Product save(Product product) {
        // 新增时设置创建时间
        if (product.getId() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    @Override
    public List<Product> listActive() {
        return productRepository.findByIsActiveTrueOrderByCreatedAtDesc();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByShopId(Long shopId) {
        return productRepository.findByShopId(shopId);
    }

    @Override
    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Product update(Long id, Product product) {
        Product db = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        db.setName(product.getName());
        db.setDescription(product.getDescription());
        db.setPrice(product.getPrice());
        db.setImageUrl(product.getImageUrl());
        db.setStock(product.getStock());
        db.setShopId(product.getShopId());
        db.setIsActive(product.getIsActive());
        db.setIsEvent(product.getIsEvent());
        // 其它字段按实际需求补充
        return productRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("商品不存在");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product toggleActive(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        p.setIsActive(!Boolean.TRUE.equals(p.getIsActive()));
        return productRepository.save(p);
    }

    @Override
    public Product toggleEvent(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        p.setIsEvent(!Boolean.TRUE.equals(p.getIsEvent()));
        return productRepository.save(p);
    }

    /**
     * 保存商品并保存图片到本地
     */
    @Override
    public Product saveWithImage(Product product, MultipartFile image) {
        // 设置创建时间（只在新增时）
        if (product.getId() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }

        // 保存图片到本地
        if (image != null && !image.isEmpty()) {
            // 推荐绝对路径
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "products";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // 获取后缀
            String ext = "";
            String original = image.getOriginalFilename();
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = UUID.randomUUID() + ext;
            File dest = new File(dir, filename);

            try {
                image.transferTo(dest);
                product.setImageUrl("/uploads/products/" + filename); // 前端访问
            } catch (IOException e) {
                throw new RuntimeException("图片上传失败", e);
            }
        }

        return productRepository.save(product);
    }

    /**
     * 兼容你 Controller 里直接参数式调用
     */
    @Override
    public Product saveWithImage(Long shopId, String name, Double price, Integer stock, MultipartFile image) {
        Product product = new Product();
        product.setShopId(shopId);
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        // 可加更多字段...
        return saveWithImage(product, image);
    }
}
