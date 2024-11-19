package com.thesearch.mylaptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategoryName(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByBrandAndCategoryName(String brand,String category);
    List<Product> findByName(String name);
    List<Product> findByBrandAndName(String brand,String name);
    Long countByBrandAndName(String brand,String name);
    boolean existsByNameAndBrand(String name,String brand);
}
