package com.sales.shopapp.repository;

import com.sales.shopapp.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderDetailId(Long orderId);
}
