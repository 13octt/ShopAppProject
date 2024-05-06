package com.sales.shopapp.controller;

import com.sales.shopapp.dto.OrderDetailDto;
import com.sales.shopapp.entity.OrderDetail;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.response.OrderDetailResponse;
import com.sales.shopapp.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(orderDetail));
    }

//    @GetMapping("/order/{orderId}")
//    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable("orderId") Long orderId) {
//        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
//        List<OrderDetailResponse> orderDetailResponses = orderDetails
//                .stream()
//                .map(OrderDetailResponse::fromOrderDetail)
//                .toList();
//        return ResponseEntity.ok(orderDetailResponses);
//    }

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDto orderDetailDto) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDto);
            return ResponseEntity.ok().body(OrderDetailResponse.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOrderDetail(@Valid @PathVariable("id") Long userId, OrderDetailDto orderDetailDto) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(userId, orderDetailDto);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@Valid @PathVariable("id") Long userId) {
        orderDetailService.deleteById(userId);
        return ResponseEntity.ok().body("Delete Order detail with id : " + userId + " successfully");
    }
}
