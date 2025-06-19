package com.telus.ShoppingCart.dto.mapper;

import com.telus.ShoppingCart.dto.UserDTO;
import com.telus.ShoppingCart.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString()); // Convert enum to String
        // Do NOT include password unless necessary (for security)
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        
        return user;
    }
}
