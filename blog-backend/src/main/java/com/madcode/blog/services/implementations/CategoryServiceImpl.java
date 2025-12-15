package com.madcode.blog.services.implementations;

import com.madcode.blog.domain.dtos.CategoryDto;
import com.madcode.blog.domain.entities.Category;
import com.madcode.blog.repositories.CategoryRepository;
import com.madcode.blog.repositories.projections.CategoryPostCountProjection;
import com.madcode.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> listCategories() {
        List<CategoryPostCountProjection> projections =
                categoryRepository.findAllWithPostCount();

        return projections.stream()
                .map(p -> CategoryDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .postCount(p.getPostCount())
                        .build())
                .toList();
    }



    @Override
    @Transactional
    public Category createCategory(Category category) {
        String categoryName = category.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)){
            throw new IllegalArgumentException("Category already exists with name: "+categoryName);
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            if(!category.get().getPosts().isEmpty()){
                throw new IllegalArgumentException("Category has posts associated with it");
            }
            categoryRepository.deleteById(id);
        }
    }

    @Override
    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: "+id));
    }
}
