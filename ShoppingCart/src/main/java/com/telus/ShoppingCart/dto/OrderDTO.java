package com.telus.ShoppingCart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;
    private List<OrderItemDTO> orderItems; // You'll create this DTO similarly
}
