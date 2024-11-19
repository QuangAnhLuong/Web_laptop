package com.thesearch.mylaptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.CartItem;

public interface  CartItemRepository extends JpaRepository<CartItem, Long>{
    void deleteAllByCartId(Long id);
}
