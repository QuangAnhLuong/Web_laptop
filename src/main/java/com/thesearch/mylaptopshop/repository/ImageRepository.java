package com.thesearch.mylaptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
