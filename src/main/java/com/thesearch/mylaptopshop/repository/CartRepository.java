package com.thesearch.mylaptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.Cart;

public interface  CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
