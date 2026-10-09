package com.sht.service.impl;

import com.sht.entity.Product;
import com.sht.mapper.ProductMapper;
import com.sht.service.OrderService;
import com.sht.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> searchByKeyword(String keyword, int limit) {
        return productMapper.searchByKeyword(keyword, limit);
    }
}
