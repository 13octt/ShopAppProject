package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.OrderDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.response.OrderResponse;

import java.util.List;

public interface IOrderService {

    Order createOrder(OrderDto orderDto) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDto orderDto);

    List<Order> getAllOrders(Long userId);
}
