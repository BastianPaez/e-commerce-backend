package com.desknet.mapper;

import com.desknet.dto.request.CategoryRequestDto;
import com.desknet.dto.request.ProductRequestDto;
import com.desknet.dto.response.CategoryResponseDto;
import com.desknet.dto.response.ProductResponseDto;
import com.desknet.model.Category;
import com.desknet.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category ToEntity(CategoryRequestDto dto){

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return category;
    }

    public static CategoryResponseDto toDto(Category category){
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription());
    }

    public static List<CategoryResponseDto> toDtoList(List<Category> categories){

        List<CategoryResponseDto> categoriesList = new ArrayList<>();

        for (Category category : categories){
            categoriesList.add(toDto(category));
        }

        return categoriesList;
    }



}
