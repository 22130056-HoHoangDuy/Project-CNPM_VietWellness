package com.vietwellness.community.controller;

import com.vietwellness.community.entity.Report;
import com.vietwellness.community.service.ReportService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller xử lý các API liên quan đến báo cáo (Report).
 *
 * Đường dẫn gốc: /api/reports
 * Cung cấp các chức năng:
 * - Tạo báo cáo
 * - Xem chi tiết báo cáo theo ID
 * - Lấy danh sách tất cả báo cáo
 * - Cập nhật báo cáo
 * - Xoá báo cáo
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    /**
     * Constructor để inject ReportService vào controller.
     *
     * @param reportService Dịch vụ xử lý logic báo cáo
     */
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    /**
     * API tạo mới một báo cáo.
     *
     * @param report Đối tượng báo cáo nhận từ request body
     * @return Báo cáo sau khi được tạo thành công
     */
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report){
        Report created = reportService.createReport(report);
        return ResponseEntity.ok(created);
    }

    /**
     * API lấy thông tin báo cáo theo ID.
     *
     * @param id ID của báo cáo
     * @return Trả về báo cáo nếu tìm thấy, ngược lại trả về 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id){
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * API lấy danh sách tất cả các báo cáo trong hệ thống.
     *
     * @return Danh sách báo cáo
     */
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }

    /**
     * API cập nhật thông tin của một báo cáo.
     *
     * @param id ID của báo cáo cần cập nhật
     * @param report Dữ liệu báo cáo mới
     * @return Báo cáo đã được cập nhật
     */
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report report){
        report.setId(id); // Đảm bảo set đúng ID cho báo cáo cần cập nhật
        Report updated = reportService.updateReport(report);
        return ResponseEntity.ok(updated);
    }

    /**
     * API xoá một báo cáo theo ID.
     *
     * @param id ID của báo cáo cần xoá
     * @return Trả về 204 No Content nếu xoá thành công
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id){
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}
