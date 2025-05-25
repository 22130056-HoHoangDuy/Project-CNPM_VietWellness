package com.vietwellness.community.utils;

import com.vietwellness.community.dto.ReportDto;
import com.vietwellness.community.entity.Report;

public class ReportMapper {
    public static ReportDto toDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setReason(report.getReason());
        dto.setReportedAt(report.getReportedAt());

        if (report.getReportedPost() != null)
            dto.setPostId(report.getReportedPost().getId());

        if (report.getReportedBy() != null)
            dto.setUserId(report.getReportedBy().getId());

        return dto;
    }

    public static Report toEntity(ReportDto dto) {
        Report report = new Report();
        report.setId(dto.getId());
        report.setReason(dto.getReason());
        report.setReportedAt(dto.getReportedAt());
        return report;
    }
}
