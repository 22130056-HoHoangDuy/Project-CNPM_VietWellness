package com.vietwellness.community.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Long id;
    private String reason;
    private Long postId;
    private Long userId; // ✅ thêm nếu thiếu
    private LocalDateTime reportedAt; // ✅ thêm nếu thiếu
}
