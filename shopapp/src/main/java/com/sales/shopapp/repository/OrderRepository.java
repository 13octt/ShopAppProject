package com.sales.shopapp.repository;

import com.sales.shopapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderId(Long orderId);
}
