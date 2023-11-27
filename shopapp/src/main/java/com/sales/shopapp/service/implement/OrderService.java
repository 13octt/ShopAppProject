package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.OrderDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.entity.OrderStatus;
import com.sales.shopapp.entity.User;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.repository.OrderRepository;
import com.sales.shopapp.repository.UserRepository;
import com.sales.shopapp.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order createOrder(OrderDto orderDto) throws Exception{
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new DataNotFoundException(
                "Can not find user with id: " + orderDto.getUserId()
        ));
//        OrderDto --> Order
        modelMapper.typeMap(OrderDto.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setOrderId));
        Order order = new Order();
        modelMapper.map(orderDto, order);
        order.setUserId(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDto.getShippingDate() == null ? LocalDate.now() : orderDto.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order getOrder(Long id) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, OrderDto orderDto) {
        return null;
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return null;
    }
}
