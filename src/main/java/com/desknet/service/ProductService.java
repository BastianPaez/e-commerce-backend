package com.desknet.service;

import com.desknet.dto.request.ProductRequestDto;
import com.desknet.dto.response.ProductResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    public void createProduct(ProductRequestDto dto);
    public List<ProductResponseDto> getAllProducts();
    public List<ProductResponseDto> getProductsByCategory(UUID categoryId);
    public ProductResponseDto getProductById(UUID id);
    public ProductResponseDto updateProductById(UUID id, ProductRequestDto dto);
    public void deleteProductById(UUID id);

}
