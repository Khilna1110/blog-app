package com.khilna.blog_app.controllers;

import com.khilna.blog_app.domain.dto.CategoryDto;
import com.khilna.blog_app.domain.dto.CreateCategoryRequest;
import com.khilna.blog_app.domain.entities.Category;
import com.khilna.blog_app.mappers.CategoryMapper;
import com.khilna.blog_app.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<CategoryDto> categories = categoryService.listCategories()
                .stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CreateCategoryRequest createCategoryRequest
    ) {
        Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
        CategoryDto savedCategory = categoryMapper.toDto(categoryService.createCategory(categoryToCreate));

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/{category-id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("category-id") UUID category_id) {
        categoryService.deleteCategory(category_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
