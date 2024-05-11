package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.request.OrderDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.exception.DataNotFoundException;

public interface IOrderService {

    Order createOrder(OrderDto orderDto) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException;

    void deleteOrder(Long orderId);
//    List<Order> findByUserId(Long userId);
}
