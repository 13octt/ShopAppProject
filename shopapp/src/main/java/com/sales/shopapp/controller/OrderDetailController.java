package com.sales.shopapp.controller;

import com.sales.shopapp.dto.OrderDetailDto;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
public class OrderDetailController {

    @GetMapping("")
    public ResponseEntity<String> getOrderDetail(){
        return  ResponseEntity.ok("Get Order detail successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderDetailById(@Valid @PathVariable("id") Long userId){
        return ResponseEntity.ok("get Order detail by user with id: " + userId);
    }

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid OrderDetailDto orderDetailDto, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("Create order detail successfully");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editOrderDetail(@Valid @PathVariable("id") Long userId, OrderDetailDto orderDetailDto){
        return ResponseEntity.ok("Edit order detail successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@Valid @PathVariable("id") Long userId){
        return ResponseEntity.ok(("Delete order detail successfully"));
    }
}
