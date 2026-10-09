package com.sht.mapper;

import com.sht.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product" +
            "        WHERE (name LIKE CONCAT('%', #{keyword}, '%')" +
            "            OR description LIKE CONCAT('%', #{keyword}, '%'))" +
            "        LIMIT #{limit}")
    List<Product> searchByKeyword(String keyword, int limit);
}
