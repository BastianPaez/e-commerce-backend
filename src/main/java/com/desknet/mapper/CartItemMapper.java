package com.desknet.mapper;

import com.desknet.dto.response.CartItemResponseDto;
import com.desknet.model.CartItem;

import java.util.List;

public class CartItemMapper {
    public static CartItemResponseDto toDto(CartItem cartItem){

        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(cartItem.getId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setProductName(cartItem.getProduct().getName());
        return dto;
    }
}
