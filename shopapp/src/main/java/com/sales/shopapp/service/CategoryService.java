package com.sales.shopapp.service;

import com.sales.shopapp.dto.request.CategoryDto;
import com.sales.shopapp.entity.Category;
import com.sales.shopapp.repository.CategoryRepository;
import com.sales.shopapp.service.implement.ICategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(CategoryDto categoryDto) {
        Category newCategory = Category.builder()
                .name(categoryDto.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category existsCategory = getCategoryById(categoryId);
        existsCategory.setName(categoryDto.getName());
        categoryRepository.save(existsCategory);
        return existsCategory;
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
