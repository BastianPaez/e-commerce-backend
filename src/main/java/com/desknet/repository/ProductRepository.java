package com.desknet.repository;

import com.desknet.dto.response.ProductResponseDto;
import com.desknet.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    public List<Product> findByCategoryId(UUID id);
}
