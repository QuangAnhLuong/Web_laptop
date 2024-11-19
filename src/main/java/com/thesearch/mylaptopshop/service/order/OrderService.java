package com.thesearch.mylaptopshop.service.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.thesearch.mylaptopshop.dto.OrderDto;
import com.thesearch.mylaptopshop.enums.OrderStatus;
import com.thesearch.mylaptopshop.exception.ResourceNotFoundException;
import com.thesearch.mylaptopshop.model.Cart;
import com.thesearch.mylaptopshop.model.Order;
import com.thesearch.mylaptopshop.model.OrderItem;
import com.thesearch.mylaptopshop.model.Product;
import com.thesearch.mylaptopshop.repository.OrderRepository;
import com.thesearch.mylaptopshop.repository.ProductRepository;
import com.thesearch.mylaptopshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder( Long userId){
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(caculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem ->{
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(order,product,cartItem.getQuantity(),cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal caculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList
            .stream()
            .map(item -> item.getPrice()
            .multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrder(Long orderId){
        return orderRepository.findById(orderId)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order Not Found!"));
    }
    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }

    @Override
    public OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }
}
