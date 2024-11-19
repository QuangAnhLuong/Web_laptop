package com.thesearch.mylaptopshop.service.cart;

import java.math.BigDecimal;

import com.thesearch.mylaptopshop.dto.CartDto;
import com.thesearch.mylaptopshop.model.Cart;
import com.thesearch.mylaptopshop.model.User;

public interface  ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);
    CartDto convertCartToDto(Cart cart);
}
