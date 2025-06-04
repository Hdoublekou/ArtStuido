package com.artstudio.backend.controller;

import com.artstudio.backend.model.Order;
import com.artstudio.backend.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import com.artstudio.backend.dto.CreateOrderRequest;
import com.artstudio.backend.dto.ShipOrderRequest; // 👈外部DTO

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 下单（创建订单）
    @PostMapping("/create")
    public Order createOrder(@RequestBody CreateOrderRequest req) {
        Order order = new Order();
        order.setUserId(req.getUserId());
        order.setWorkId(req.getWorkId());
        order.setTotalPrice(req.getTotalPrice());
        order.setStatus("CREATED");
        order.setCreatedAt(LocalDateTime.now());
        order.setPaidAt(null);
        order.setCancelledAt(null);
        return orderRepository.save(order);
    }

    // 模拟支付（订单支付）
    @PostMapping("/pay/{orderId}")
    public Order payOrder(@PathVariable Long orderId) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            if (!"CREATED".equals(order.getStatus())) {
                return order; // 只能支付CREATED状态
            }
            order.setStatus("PAID");
            order.setPaidAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }

    // 取消订单
    @PostMapping("/cancel/{orderId}")
    public Order cancelOrder(@PathVariable Long orderId) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            if (!"CREATED".equals(order.getStatus())) {
                return order; // 只能取消CREATED状态
            }
            order.setStatus("CANCELLED");
            order.setCancelledAt(LocalDateTime.now());
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }

    // 查询用户所有订单
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    // 店铺订单一览
    @GetMapping("/shop/{shopId}")
    public List<Order> getShopOrders(@PathVariable Long shopId) {
        return orderRepository.findByShopId(shopId);
    }

    // 订单发货
    @PutMapping("/{orderId}/ship")
    public Order shipOrder(@PathVariable Long orderId, @RequestBody ShipOrderRequest req) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setShipperId(req.getShipperId());
        order.setShippingStatus("已发货");
        order.setShippedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}
