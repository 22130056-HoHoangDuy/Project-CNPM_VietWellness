package com.vietwellness.community.utils;

import com.vietwellness.community.dto.UserDto;
import com.vietwellness.community.entity.User;

public class UserMapper {

    /**
     * Chuyển đổi từ Entity User sang DTO UserDto
     * @param user đối tượng User Entity
     * @return đối tượng UserDto tương ứng
     */
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        return dto;
    }

    /**
     * Chuyển đổi từ DTO UserDto sang Entity User
     * @param dto đối tượng UserDto
     * @return đối tượng User Entity tương ứng
     */
    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setAvatarUrl(dto.getAvatarUrl());
        return user;
    }
}
