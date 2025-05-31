package com.desknet.dto.request;


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
public class ProductRequestDto {

    @NotBlank
    private String name;


    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int stock;

    @NotNull
    private UUID categoryId;

}
