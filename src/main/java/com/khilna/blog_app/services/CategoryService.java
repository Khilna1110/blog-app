package com.khilna.blog_app.services;

import com.khilna.blog_app.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<Category> listCategories();

    Category createCategory(Category category);

    void deleteCategory(UUID id);

    Category getCategoryById(UUID id);
}
