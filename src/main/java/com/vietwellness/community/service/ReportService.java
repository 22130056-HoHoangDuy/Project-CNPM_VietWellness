package com.vietwellness.community.service;

import com.vietwellness.community.entity.Report;
import java.util.List;
import java.util.Optional;

/**
 * Interface định nghĩa các phương thức nghiệp vụ liên quan đến Report (Báo cáo).
 */
public interface ReportService {

    /**
     * Tạo mới một báo cáo.
     * @param report đối tượng Report cần tạo
     * @return Report đã được tạo
     */
    Report createReport(Report report);

    /**
     * Lấy thông tin báo cáo theo id.
     * @param id id của báo cáo
     * @return Optional chứa Report nếu tìm thấy, không có nếu không tìm thấy
     */
    Optional<Report> getReportById(Long id);

    /**
     * Lấy danh sách tất cả các báo cáo.
     * @return danh sách Report
     */
    List<Report> getAllReports();

    /**
     * Cập nhật thông tin báo cáo.
     * @param report đối tượng Report cần cập nhật
     * @return Report đã được cập nhật
     */
    Report updateReport(Report report);

    /**
     * Xóa báo cáo theo id.
     * @param id id của báo cáo cần xóa
     */
    void deleteReport(Long id);
}
