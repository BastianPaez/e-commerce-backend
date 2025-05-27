package com.desknet.service;

import com.desknet.dto.request.CategoryRequestDto;
import com.desknet.dto.response.CategoryResponseDto;
import com.desknet.exception.ResourceNotFoundException;
import com.desknet.mapper.CategoryMapper;
import com.desknet.model.Category;
import com.desknet.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void createCategory(CategoryRequestDto dto) {

        Category category = CategoryMapper.ToEntity(dto);

        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return CategoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryResponseDto getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id category not found: " + id));

        return CategoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto updateCategoryById(UUID id, CategoryRequestDto dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id category not found: " + id));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        categoryRepository.save(category);

        return CategoryMapper.toDto(category);

    }

    @Override
    public void deleteCategoryById(UUID id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id category not found: " + id));

        categoryRepository.delete(category);
    }
}
