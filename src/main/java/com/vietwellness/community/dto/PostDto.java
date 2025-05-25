package com.vietwellness.community.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private Long userId;
    private Long groupId;
    private String content;
    private String mediaUrl;
    private String aiStatus; // ví dụ: "APPROVED", "NEEDS_VERIFICATION"
}
