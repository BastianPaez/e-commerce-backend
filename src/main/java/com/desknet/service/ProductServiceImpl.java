package com.desknet.service;

import com.desknet.dto.request.ProductRequestDto;
import com.desknet.dto.response.ProductResponseDto;
import com.desknet.mapper.ProductMapper;
import com.desknet.model.Category;
import com.desknet.model.Product;
import com.desknet.repository.CategoryRepository;
import com.desknet.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void createProduct(ProductRequestDto dto) {


        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Product product = ProductMapper.toEntity(dto, category);

        System.out.println(product.getDescription());
        productRepository.save(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        List<Product> productList = productRepository.findAll();

        return ProductMapper.toDtoList(productList);
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(UUID categoryId) {

        List<Product> productList = productRepository.findByCategoryId(categoryId);

        return ProductMapper.toDtoList(productList);
    }

    @Override
    public ProductResponseDto getProductById(UUID id) {

        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));

        return ProductMapper.toDto(product);
    }

    @Override
    public ProductResponseDto updateProductById(UUID id, ProductRequestDto dto) {

        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id product not found: "+ dto.getCategoryId()));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Id category not found"+ id));

        Product productUpdated = ProductMapper.productRequestToEntity(product, dto, category);

        productRepository.save(productUpdated);

        return ProductMapper.toDto(productUpdated);
    }

    @Override
    public void deleteProductById(UUID id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found product id: "+ id));

        productRepository.delete(product);

    }
}
