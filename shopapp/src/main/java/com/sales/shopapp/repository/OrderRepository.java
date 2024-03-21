package com.sales.shopapp.repository;

import com.sales.shopapp.entity.Order;
import com.sales.shopapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm các đơn hàng của 1 user nào đó
//    List<Order> findByUserId(Long userId);
}
