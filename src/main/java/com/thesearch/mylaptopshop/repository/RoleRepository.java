package com.thesearch.mylaptopshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thesearch.mylaptopshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
