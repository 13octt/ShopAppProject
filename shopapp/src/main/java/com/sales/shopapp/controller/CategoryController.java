package com.sales.shopapp.controller;

import com.sales.shopapp.dto.request.CategoryDto;
import com.sales.shopapp.dto.response.CategoryResponse;
import com.sales.shopapp.entity.Category;
import com.sales.shopapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoriesList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoriesList);
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            categoryService.createCategory(categoryDto);
            return ResponseEntity.ok(
                    CategoryResponse.builder()
                            .name(categoryDto.getName())
                            .message("Product Create Successfully")
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable("id") Long categoryId,
            @Valid @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity.ok("Update category successfully with id: " + categoryId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Delete category successfully with id " + categoryId);
    }
}
