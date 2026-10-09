package com.sht.service;

import com.sht.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchByKeyword(String keyword, int limit);
}
