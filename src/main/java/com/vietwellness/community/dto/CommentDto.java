package com.vietwellness.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO này thường được sử dụng khi tạo mới, cập nhật hoặc hiển thị thông tin bình luận.
 */
@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private LocalDateTime commentedAt;
}
