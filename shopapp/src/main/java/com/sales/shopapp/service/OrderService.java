package com.sales.shopapp.service;

import com.sales.shopapp.dto.request.OrderDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.entity.OrderStatus;
import com.sales.shopapp.entity.User;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.repository.OrderRepository;
import com.sales.shopapp.repository.UserRepository;
import com.sales.shopapp.service.implement.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Order createOrder(OrderDto orderDto) throws Exception {
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + orderDto.getUserId()));
        // convert orderDTO -> Order
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setOrderId));
        Order order = new Order();
        modelMapper.map((orderDto), order);
        order.setUserId(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDto.getShippingDate() == null
                ? LocalDate.now() : orderDto.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Cannot find order with id: " + id));
        User existingUser = userRepository.findById(
                orderDto.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find user with id: " + id));

        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setOrderId));

        modelMapper.map(orderDto, order);
        order.setUserId(existingUser);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        // soft-delete
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

//    @Override
//    public List<Order> findByUserId(Long userId) {
////        return orderRepository.findByUserId(userId);
//        return orderRepository.findByUser_Id(userId);
//    }
}
