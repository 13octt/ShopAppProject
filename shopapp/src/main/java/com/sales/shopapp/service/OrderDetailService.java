package com.sales.shopapp.service;

import com.sales.shopapp.dto.OrderDetailDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.entity.OrderDetail;
import com.sales.shopapp.entity.Product;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.repository.OrderDetailRepository;
import com.sales.shopapp.repository.OrderRepository;
import com.sales.shopapp.repository.ProductRepository;
import com.sales.shopapp.service.implement.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class OrderDetailService implements IOrderDetailService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail createOrderDetail(OrderDetailDto orderDetailDto) throws Exception {
        Order order = orderRepository.findById(orderDetailDto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : " + orderDetailDto.getOrderId()));
        Product product = productRepository.findById(orderDetailDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDto.getProductId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDto.getNumberOfProduct())
                .price(orderDetailDto.getPrice())
                .totalMoney(orderDetailDto.getTotalMoney())
                .color(orderDetailDto.getColor())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws DataNotFoundException {
        return orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find OrderDetail with id: " + id));
    }

    @Override
    public OrderDetail updateOrderDetail(Long id, OrderDetailDto orderDetailDto) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));
        Order existingOrder = orderRepository.findById(orderDetailDto.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        Product existingProduct = productRepository.findById(orderDetailDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDto.getProductId()));
        existingOrderDetail.setPrice(orderDetailDto.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProduct());
        existingOrderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDto.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderDetailRepository.save(existingOrderDetail);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return null;
    }
}
