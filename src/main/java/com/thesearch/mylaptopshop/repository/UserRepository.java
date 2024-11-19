package com.thesearch.mylaptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.User;

public interface UserRepository extends  JpaRepository<User, Long>{
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
