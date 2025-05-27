package com.desknet.dto.response;

import com.desknet.model.Cart;
import com.desknet.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class CartItemResponseDto {

    private UUID id;
    private String productName;
    private int quantity;

}
