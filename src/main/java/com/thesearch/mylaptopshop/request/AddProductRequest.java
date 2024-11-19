package com.thesearch.mylaptopshop.request;

import java.math.BigDecimal;

import com.thesearch.mylaptopshop.model.Category;

import lombok.Data;

@Data
public class AddProductRequest {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;
    private String description;
    private Category category;
}
