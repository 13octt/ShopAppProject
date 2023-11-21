package com.sales.shopapp.service;

import com.sales.shopapp.dto.CategoryDto;
import com.sales.shopapp.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto category);
    Category getCategoryById(Long categoryId);
    List<Category> getAllCategory();
    Category updateCategory(Long categoryId, CategoryDto categoryDto);
    void deleteCategory(Long categoryId);
}
