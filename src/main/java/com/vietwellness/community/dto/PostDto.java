package com.vietwellness.community.dto;

import lombok.Data;

/**
 * Chứa các trường dữ liệu cần thiết để truyền tải thông tin bài đăng giữa client và server.
 */
@Data
public class PostDto {
    private Long id;
    private Long userId;
    private Long groupId;
    private String content;
    private String mediaUrl;  // URL đến media đính kèm (ảnh, video, ...)
    private String aiStatus;  // Trạng thái xử lý AI cho bài đăng, ví dụ: "APPROVED", "NEEDS_VERIFICATION"
}
