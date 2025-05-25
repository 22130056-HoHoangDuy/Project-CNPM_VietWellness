package com.vietwellness.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Chứa các thông tin cần thiết để truyền tải dữ liệu báo cáo giữa client và server.
 */
@Data
public class ReportDto {
    private Long id;                // ID báo cáo
    private String reason;          // Lý do báo cáo
    private Long postId;            // ID bài đăng bị báo cáo
    private Long userId;            // ID người dùng tạo báo cáo
    private LocalDateTime reportedAt; // Thời gian báo cáo
}
