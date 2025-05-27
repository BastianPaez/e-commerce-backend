package com.desknet.service;

import com.desknet.dto.request.CategoryRequestDto;
import com.desknet.dto.response.CategoryResponseDto;
import com.desknet.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CategoryService {

    public void createCategory (CategoryRequestDto dto);
    public List<CategoryResponseDto> getAllCategories ();
    public CategoryResponseDto getCategoryById(UUID id);
    public CategoryResponseDto updateCategoryById(UUID id, CategoryRequestDto dto);
    public void deleteCategoryById(UUID id);
}
