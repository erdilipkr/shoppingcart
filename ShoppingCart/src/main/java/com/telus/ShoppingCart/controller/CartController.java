package com.telus.ShoppingCart.controller;

import com.telus.ShoppingCart.dto.CartItemDTO;
import com.telus.ShoppingCart.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "CartController", description = "Cart Controller APIs")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add item to cart
    @Operation(summary = "Add item to cart ", description = "Add item to cart")
    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody CartItemDTO dto) {
        return cartService.addItemToCart(dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    // Get cart items for user
    @Operation(summary = "Get cart items for user", description = "Get cart items for user")
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> getUserCart(@PathVariable Long userId) {
        List<CartItemDTO> items = cartService.getCartItemsByUser(userId);
        return ResponseEntity.ok(items);
    }
//update cart item
    @Operation(summary = "update cart item", description = "update cart item")
    @PutMapping("/{itemId}")
    public ResponseEntity<CartItemDTO> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody CartItemDTO cartItemDTO) {

        CartItemDTO updatedItem = cartService.updateCartItem(itemId, cartItemDTO);
        return updatedItem != null
                ? ResponseEntity.ok(updatedItem)
                : ResponseEntity.notFound().build();
    }
    // Remove a single item
    @Operation(summary = "Remove a single item", description = "Remove a single item")
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> removeItem(@PathVariable Long id) {
        boolean deleted = cartService.removeItem(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Clear entire cart for user
    @Operation(summary = "Clear entire cart for user", description = "Clear entire cart for user")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCartByUser(userId);
        return ResponseEntity.noContent().build();
    }
}
