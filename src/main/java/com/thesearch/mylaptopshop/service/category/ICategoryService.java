package com.thesearch.mylaptopshop.service.category;

import java.util.List;

import com.thesearch.mylaptopshop.model.Category;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updatecCategory(Category category, long id);
    void deleteCategoryById(long id);
    
}
