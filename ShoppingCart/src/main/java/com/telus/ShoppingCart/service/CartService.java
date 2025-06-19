package com.telus.ShoppingCart.service;

import com.telus.ShoppingCart.dto.CartItemDTO;
import com.telus.ShoppingCart.dto.mapper.CartItemMapper;
import com.telus.ShoppingCart.model.CartItem;
import com.telus.ShoppingCart.model.Product;
import com.telus.ShoppingCart.model.User;
import com.telus.ShoppingCart.repository.CartItemRepository;
import com.telus.ShoppingCart.repository.ProductRepository;
import com.telus.ShoppingCart.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add item to cart
    public Optional<CartItemDTO> addItemToCart(CartItemDTO dto) {
        Optional<User> user = userRepository.findById(dto.getUserId());
        Optional<Product> product = productRepository.findById(dto.getProductId());

        if (user.isPresent() && product.isPresent()) {
            CartItem cartItem = CartItemMapper.toEntity(dto, user.get(), product.get());
            CartItem saved = cartItemRepository.save(cartItem);
            return Optional.of(CartItemMapper.toDTO(saved));
        }

        return Optional.empty();
    }

    // Get cart items by user ID
    public List<CartItemDTO> getCartItemsByUser(Long userId) {
        return cartItemRepository.findByUserId(userId)
                .stream()
                .map(CartItemMapper::toDTO)
                .collect(Collectors.toList());
    }
//update cart item
    
    public CartItemDTO updateCartItem(Long itemId, CartItemDTO dto) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findById(itemId);
        if (cartItemOpt.isEmpty()) {
            return null; // Or throw custom exception if preferred
        }

        CartItem cartItem = cartItemOpt.get();

        // Update quantity if provided
        if (dto.getQuantity() > 0) {
            cartItem.setQuantity(dto.getQuantity());
        }

        // Update price if provided (optional)
        if (dto.getPrice() != null) {
            cartItem.setPrice(dto.getPrice());
        }

        // You can also update User and Product associations if needed:
        if (dto.getUserId() != null && !dto.getUserId().equals(cartItem.getUser().getId())) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            cartItem.setUser(user);
        }

        if (dto.getProductId() != null && !dto.getProductId().equals(cartItem.getProduct().getId())) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            cartItem.setProduct(product);
        }

        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        return CartItemMapper.toDTO(updatedCartItem);
    }
    // Remove item from cart
    public boolean removeItem(Long id) {
        return cartItemRepository.findById(id).map(item -> {
            cartItemRepository.delete(item);
            return true;
        }).orElse(false);
    }

    // Clear cart for a user
    public void clearCartByUser(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
