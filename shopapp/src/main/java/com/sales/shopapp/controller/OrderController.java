package com.sales.shopapp.controller;

import com.sales.shopapp.dto.OrderDto;
import com.sales.shopapp.entity.Order;
import com.sales.shopapp.response.OrderResponse;
import com.sales.shopapp.service.implement.IOrderService;
import com.sales.shopapp.service.implement.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto orderDto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order order = orderService.createOrder(orderDto);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            return ResponseEntity.ok("Get order by id" + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderById(@Valid @PathVariable("id") Long userID, @Valid @RequestBody OrderDto orderDto) {
        // Xóa mềm
        return ResponseEntity.ok("Update order by Id");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderById(@Valid @PathVariable("id") Long userId) {
        return ResponseEntity.ok("Delete order successfully with id: " + userId);
    }


}
