package com.sales.shopapp.repository;

import com.sales.shopapp.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
//    List<OrderDetail> findByOrderId(Long orderId);
}
