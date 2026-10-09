package com.sht.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String trackingNumber;
    private LocalDate estimatedDelivery;
    private LocalDateTime createdAt;
}