package com.vietwellness.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;
    private LocalDateTime commentedAt; // ✅ Thêm dòng này
}
