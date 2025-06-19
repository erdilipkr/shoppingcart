package com.telus.ShoppingCart.dto.mapper;

import com.telus.ShoppingCart.dto.OrderItemDTO;
import com.telus.ShoppingCart.model.Order;
import com.telus.ShoppingCart.model.OrderItem;
import com.telus.ShoppingCart.model.Product;

public class OrderItemMapper {

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        if (orderItem == null) return null;

        return new OrderItemDTO(
            orderItem.getId(),
            orderItem.getProduct().getId(),
            orderItem.getQuantity(),
            orderItem.getPrice()
        );
    }

    public static OrderItem toEntity(OrderItemDTO dto, Order order, Product product) {
        if (dto == null || order == null || product == null) return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());

        return orderItem;
    }
}
