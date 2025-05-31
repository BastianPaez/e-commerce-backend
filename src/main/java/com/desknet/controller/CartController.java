package com.desknet.controller;

import com.desknet.dto.response.CartItemResponseDto;
import com.desknet.dto.response.ProductResponseDto;
import com.desknet.model.CartItem;
import com.desknet.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Void> createCart(@PathVariable UUID userId){

        cartService.createCart(userId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/guest")
    public ResponseEntity<UUID> createGuestCart(){
        UUID cartId = cartService.createGuestCart();

        return ResponseEntity.status(HttpStatus.CREATED).body(cartId);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<List<CartItemResponseDto>> getCart(@PathVariable UUID cartId){

        List<CartItemResponseDto> cartItemList = cartService.getCart(cartId);

        return ResponseEntity.ok().body(cartItemList);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<Void> addCartItem(@PathVariable UUID cartId, @RequestParam UUID productId, @RequestParam int quantity){
        cartService.addItem(cartId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable UUID cartId, @PathVariable UUID productId, @RequestParam int quantity){
        cartService.updateCartItemQuantity(cartId,productId,quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID cartId, @PathVariable UUID productId){
        cartService.deleteCartItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }
}
