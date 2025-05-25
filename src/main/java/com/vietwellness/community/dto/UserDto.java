package com.vietwellness.community.dto;

import lombok.Data;

/**
 * Chứa thông tin cần thiết để truyền dữ liệu người dùng giữa client và server.
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String avatarUrl;
}
