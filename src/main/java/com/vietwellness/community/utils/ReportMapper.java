package com.vietwellness.community.utils;

import com.vietwellness.community.dto.ReportDto;
import com.vietwellness.community.entity.Report;

public class ReportMapper {

    /**
     * Chuyển đổi từ Entity Report sang DTO ReportDto
     * @param report đối tượng Report Entity
     * @return đối tượng ReportDto tương ứng
     */
    public static ReportDto toDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setReason(report.getReason());
        dto.setReportedAt(report.getReportedAt()); // Thời điểm báo cáo

        if (report.getReportedPost() != null)
            dto.setPostId(report.getReportedPost().getId()); // ID bài viết bị báo cáo

        if (report.getReportedBy() != null)
            dto.setUserId(report.getReportedBy().getId()); // ID người báo cáo

        return dto;
    }

    /**
     * Chuyển đổi từ DTO ReportDto sang Entity Report
     * @param dto đối tượng ReportDto
     * @return đối tượng Report Entity tương ứng
     */
    public static Report toEntity(ReportDto dto) {
        Report report = new Report();
        report.setId(dto.getId());
        report.setReason(dto.getReason());
        report.setReportedAt(dto.getReportedAt()); // Thời điểm báo cáo
        return report;
    }
}
