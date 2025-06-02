package com.khilna.blog_app.services.impl;

import com.khilna.blog_app.domain.entities.Category;
import com.khilna.blog_app.repositories.CategoryRepository;
import com.khilna.blog_app.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Transactional
    @Override
    public Category createCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName()))
            throw new IllegalArgumentException("Category already exists with name: " + category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            if (!category.get().getPosts().isEmpty())
                throw new IllegalStateException("Category has posts associated with it");
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found with id: " + id));
    }
}
