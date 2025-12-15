package com.madcode.blog.services;

import com.madcode.blog.domain.dtos.CategoryDto;
import com.madcode.blog.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDto> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
    Category getCategoryById(UUID id);
}
