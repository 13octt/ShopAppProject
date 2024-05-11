package com.sales.shopapp.repository;

import com.sales.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm các đơn hàng của 1 user nào đó
//    List<Order> findByUserId(Long userId);
//    List<Order> findByUser_Id(Long userId);

//    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
//    List<Order> findOrdersByUser_Id(@Param("userId") Long userId);

}
