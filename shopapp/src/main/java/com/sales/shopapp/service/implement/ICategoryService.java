package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.request.CategoryDto;
import com.sales.shopapp.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDto category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategory();

    Category updateCategory(Long categoryId, CategoryDto categoryDto);

    void deleteCategory(Long categoryId);
}
