package com.thesearch.mylaptopshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thesearch.mylaptopshop.dto.OrderDto;
import com.thesearch.mylaptopshop.exception.ResourceNotFoundException;
import com.thesearch.mylaptopshop.model.Order;
import com.thesearch.mylaptopshop.response.ApiResponse;
import com.thesearch.mylaptopshop.service.order.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try{
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order Success!",orderDto));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error Occured",e.getMessage()));
        }
    }
    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try{OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(new ApiResponse("Success",order));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }
    }

    @GetMapping("/{userId}/user_order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
        try{List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse("Success",orders));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }
    }
    
}
