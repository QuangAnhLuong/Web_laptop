package com.thesearch.mylaptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}
