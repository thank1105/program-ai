package com.sht.service;

import com.sht.entity.Order;

import java.util.List;

public interface OrderService {
    Order findById(String orderId);

    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId, int safeLimit);
}
