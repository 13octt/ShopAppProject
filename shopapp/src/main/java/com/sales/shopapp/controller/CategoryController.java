package com.sales.shopapp.controller;

import com.sales.shopapp.dto.CategoryDto;
import com.sales.shopapp.entity.Category;
import com.sales.shopapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor

//@Validated
public class CategoryController {

    // Dependency Injection
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        List<Category> categoriesList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoriesList.toString());
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
            return ResponseEntity.ok("Product create successfully");
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
