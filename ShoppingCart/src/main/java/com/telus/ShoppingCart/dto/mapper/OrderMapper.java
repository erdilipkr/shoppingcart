package com.telus.ShoppingCart.dto.mapper;

import com.telus.ShoppingCart.dto.OrderDTO;
import com.telus.ShoppingCart.dto.OrderItemDTO;
import com.telus.ShoppingCart.model.Order;
import com.telus.ShoppingCart.model.OrderItem;
import com.telus.ShoppingCart.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;

        List<OrderItemDTO> orderItemDTOs = null;
        if (order.getOrderItems() != null) {
            orderItemDTOs = order.getOrderItems()
                .stream()
                .map(OrderMapper::toOrderItemDTO)
                .collect(Collectors.toList());
        }

        return new OrderDTO(
            order.getId(),
            order.getUser().getId(),
            order.getOrderDate(),
            order.getTotal(),
            order.getStatus(),
            orderItemDTOs
        );
    }

    public static Order toEntity(OrderDTO dto, User user) {
        if (dto == null || user == null) return null;

        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(user);
        order.setOrderDate(dto.getOrderDate());
        order.setTotal(dto.getTotal());
        order.setStatus(dto.getStatus());

        if (dto.getOrderItems() != null) {
            List<OrderItem> orderItems = dto.getOrderItems()
                .stream()
                .map(oiDTO -> toOrderItemEntity(oiDTO, order))
                .collect(Collectors.toList());
            order.setOrderItems(orderItems);
        }

        return order;
    }

    private static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) return null;
        return new OrderItemDTO(
            orderItem.getId(),
            orderItem.getProduct().getId(),
            orderItem.getQuantity(),
            orderItem.getPrice()
        );
    }

    private static OrderItem toOrderItemEntity(OrderItemDTO dto, Order order) {
        if (dto == null || order == null) return null;

        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setOrder(order);
        // For product, you'd typically fetch product entity by dto.getProductId() before setting it here.
        // e.g., orderItem.setProduct(productService.findById(dto.getProductId()));

        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());

        return orderItem;
    }
}
