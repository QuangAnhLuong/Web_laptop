package com.thesearch.mylaptopshop.service.order;

import java.util.List;

import com.thesearch.mylaptopshop.dto.OrderDto;
import com.thesearch.mylaptopshop.model.Order;

public interface IOrderService {
    Order placeOrder( Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
