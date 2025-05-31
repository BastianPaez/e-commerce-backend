package com.desknet.mapper;

import com.desknet.dto.request.ProductRequestDto;
import com.desknet.dto.response.ProductResponseDto;
import com.desknet.model.Category;
import com.desknet.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product toEntity(ProductRequestDto dto, Category category){

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);


        return product;
    }

    public static ProductResponseDto toDto(Product product){

        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setCategoryName(product.getCategory().getName());

        return productDto;
    }

    public static List<ProductResponseDto> toDtoList(List<Product> productList){

        List<ProductResponseDto> productDtoList = new ArrayList<>();

        for (Product product : productList){
            productDtoList.add(toDto(product));
        }

        return productDtoList;
    }

    public static Product productRequestToEntity(Product product, ProductRequestDto dto, Category category){

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);


        return product;
    }

}
