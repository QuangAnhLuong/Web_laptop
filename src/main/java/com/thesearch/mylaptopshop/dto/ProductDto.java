package com.thesearch.mylaptopshop.dto;


import java.math.BigDecimal;
import java.util.List;

import com.thesearch.mylaptopshop.model.Category;

import lombok.Data;

@Data
public class ProductDto {
    private long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;
    private String description;
    private Category category;
    private List<ImageDto> image;
}
