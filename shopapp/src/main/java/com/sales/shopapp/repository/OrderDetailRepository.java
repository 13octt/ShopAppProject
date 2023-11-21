package com.sales.shopapp.repository;

import com.sales.shopapp.model.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderDetailId(Long orderId);
}
