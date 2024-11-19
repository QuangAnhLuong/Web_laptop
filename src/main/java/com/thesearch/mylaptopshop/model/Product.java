package com.thesearch.mylaptopshop.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int quantity;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy="product",cascade = CascadeType.ALL,orphanRemoval= true)
    private List<Image> image;
    public Product(String name, String brand,BigDecimal price,int quantity,String description, Category category){
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
    }
}
