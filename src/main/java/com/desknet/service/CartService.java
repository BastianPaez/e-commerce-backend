package com.desknet.service;

import com.desknet.dto.response.CartItemResponseDto;
import com.desknet.model.Cart;
import com.desknet.model.CartItem;

import java.util.List;
import java.util.UUID;

public interface CartService {

    public void createCart(UUID userId);
    public void addItem(UUID cartId, UUID productId, int quantity);
    public List<CartItemResponseDto> getCart(UUID cartId);
    public void clearCart (UUID CartId);
    public void deleteCartItem (UUID cartId, UUID productId, int quantity );
}
