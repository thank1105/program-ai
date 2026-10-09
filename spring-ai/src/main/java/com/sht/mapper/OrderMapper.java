package com.sht.mapper;

import com.sht.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("select * from order_info where id = #{orderId}")
    Order findById(String orderId);

    @Select("SELECT * FROM order_info WHERE user_id = #{userId} " +
            "ORDER BY created_at DESC LIMIT #{safeLimit}")
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId, int safeLimit);
}
