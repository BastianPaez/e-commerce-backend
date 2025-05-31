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
import com.desknet.utils.RepositoryUtils;
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
        User user = RepositoryUtils.findOrThrow(userRepository, userId, "User");

        Optional<Cart> cartExist = cartRepository.findByUserId(userId);
        if (cartExist.isPresent()){
            throw new IllegalStateException("User already has a cart");
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
    }

    public UUID createGuestCart() {
        Cart cart = new Cart();
        cart.setUser(null); 
        cartRepository.save(cart);
        return cart.getId();
    }

    @Override
    public void addItem(UUID cartId, UUID productId, int quantity) {
        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");
        Product product = RepositoryUtils.findOrThrow(productRepository, productId, "Product");

        int newQuantity;

        boolean itemExist = false;

        for ( CartItem item : cart.getCartItemList()){
            if (item.getProduct().getId().equals(productId)){

                newQuantity = item.getQuantity() + quantity;

                if (newQuantity > product.getStock()){
                    throw new UnsupportedOperationException("Product not enough");
                }

                item.setQuantity(newQuantity);
                cartItemRepository.save(item);
                itemExist = true;
                break;
            }
        }

        if (!itemExist){
            newQuantity = quantity;

            if (newQuantity > product.getStock()){
                throw new UnsupportedOperationException("Product not enough");
            }

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
        }
    }

    @Override
    public List<CartItemResponseDto> getCart(UUID cartId) {

        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");

        List<CartItemResponseDto> cartItemList = new ArrayList<>();

        for (CartItem item: cart.getCartItemList()){
            cartItemList.add(CartItemMapper.toDto(item));
        }

        return cartItemList;
    }

    @Override
    public void clearCart(UUID cartId) {
        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");

        List<CartItem> items = cart.getCartItemList();

        items.clear();

        cartRepository.save(cart);
    }



    @Override
    public void updateCartItemQuantity(UUID cartId, UUID productId, int quantity) {
        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");
        Product product = RepositoryUtils.findOrThrow(productRepository, productId, "Product");

        for (CartItem item : cart.getCartItemList()){
            if (item.getProduct().getId().equals(product.getId())){
                if (quantity > product.getStock()){
                    throw new UnsupportedOperationException("Product not enough");
                }
                item.setQuantity(quantity);
                cartItemRepository.save(item);
                break;
            }
        }

    }

    @Override
    public void deleteCartItem(UUID cartId, UUID cartItemId) {
        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");
        cartItemRepository.deleteById(cartItemId);

    }

//    @Override
//    public void decreaseCartItemQuantity(UUID cartId, UUID productId) {
//        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");
//        Product product = RepositoryUtils.findOrThrow(productRepository, productId, "product");
//
//        for (CartItem item : cart.getCartItemList()){
//            if (product.getId().equals(item.getProduct().getId())){
//                item.setQuantity(item.getQuantity() - 1);
//                if (item.getQuantity() <= 0){
//                    cartItemRepository.delete(item);
//                } else {
//                    cartItemRepository.save(item);
//                }
//                break;
//            }
//        }
//
//    }
//    @Override
//    public void incrementCartItem(UUID cartId, UUID productId) {
//        Cart cart = RepositoryUtils.findOrThrow(cartRepository, cartId, "Cart");
//        Product product = RepositoryUtils.findOrThrow(productRepository, productId, "product");
//
//        for (CartItem item : cart.getCartItemList()){
//            if (product.getId().equals(item.getProduct().getId())){
//
//                int newQuantity = item.getQuantity() + 1;
//
//                if (newQuantity > product.getStock()){
//                    throw new UnsupportedOperationException("Product not enough");
//                }
//
//                item.setQuantity(newQuantity);
//                cartItemRepository.save(item);
//
//                break;
//            }
//        }
//    }


}
