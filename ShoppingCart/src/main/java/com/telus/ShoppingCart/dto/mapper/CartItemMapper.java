package com.telus.ShoppingCart.dto.mapper;

import com.telus.ShoppingCart.dto.CartItemDTO;
import com.telus.ShoppingCart.model.CartItem;
import com.telus.ShoppingCart.model.Product;
import com.telus.ShoppingCart.model.User;

public class CartItemMapper {

    // Convert Entity → DTO
    public static CartItemDTO toDTO(CartItem cartItem) {
        if (cartItem == null) return null;

        return new CartItemDTO(
            cartItem.getId(),
            cartItem.getUser().getId(),
            cartItem.getProduct().getId(),
            cartItem.getQuantity(),
            cartItem.getPrice()
        );
    }

    // Convert DTO → Entity
    public static CartItem toEntity(CartItemDTO dto, User user, Product product) {
        if (dto == null || user == null || product == null) return null;

        CartItem cartItem = new CartItem();
        cartItem.setId(dto.getId());
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setPrice(dto.getPrice());

        return cartItem;
    }
}
