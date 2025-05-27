package com.desknet.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class ProductResponseDto {

    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    private int stock;

    private String categoryName;
}
