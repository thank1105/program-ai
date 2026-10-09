package com.sht.service.impl;


import com.sht.entity.Order;
import com.sht.mapper.OrderMapper;
import com.sht.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Order findById(String orderId) {
        return orderMapper.findById(orderId);
    }

    @Override
    public List<Order> findByUserIdOrderByCreatedAtDesc(Long userId, int safeLimit) {
        return orderMapper.findByUserIdOrderByCreatedAtDesc(userId, safeLimit);
    }
}
