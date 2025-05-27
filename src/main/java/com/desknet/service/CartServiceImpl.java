package com.desknet.service;

import com.desknet.dto.response.CartItemResponseDto;
import com.desknet.exception.ResourceNotFoundException;
import com.desknet.mapper.CartItemMapper;
import com.desknet.model.Cart;
import com.desknet.model.CartItem;
import com.desknet.model.Product;
import com.desknet.model.User;
import com.desknet.repository.CartItemRepository;
import com.desknet.repository.CartRepository;
import com.desknet.repository.ProductRepository;
import com.desknet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void createCart(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Not found userId: "+ userId));

        Optional<Cart> cartExist = cartRepository.findByUserId(userId);
        if (cartExist.isPresent()){
            throw new IllegalStateException("User already has a cart");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    @Override
    public void addItem(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart id not found "+ cartId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new  ResourceNotFoundException("Product id not found "+ productId));

        boolean itemExist = false;

        for ( CartItem item : cart.getCartItemList()){
            if (item.getProduct().getId().equals(productId)){
                item.setQuantity(item.getQuantity() + quantity);
                cartItemRepository.save(item);
                itemExist = true;
                break;
            }
        }

        if (!itemExist){
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    @Override
    public List<CartItemResponseDto> getCart(UUID cartId) {

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart id not found "+cartId));

        List<CartItemResponseDto> cartItemList = new ArrayList<>();

        for (CartItem item: cart.getCartItemList()){
            cartItemList.add(CartItemMapper.toDto(item));
        }

        return cartItemList;
    }

    @Override
    public void clearCart(UUID CartId) {

    }

    @Override
    public void deleteCartItem(UUID cartId, UUID productId, int quantity) {

    }


}
