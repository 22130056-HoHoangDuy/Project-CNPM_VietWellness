package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Report;
import com.vietwellness.community.repository.ReportRepository;
import com.vietwellness.community.service.ReportService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Triển khai (implementation) của ReportService.
 * Xử lý nghiệp vụ liên quan đến báo cáo (Report).
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    /**
     * Constructor để inject ReportRepository.
     * @param reportRepository repository thao tác với Report
     */
    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    /**
     * Tạo báo cáo mới.
     * @param report đối tượng Report cần lưu
     * @return Report đã được lưu
     */
    @Override
    public Report createReport(Report report){
        return reportRepository.save(report);
    }

    /**
     * Lấy báo cáo theo id.
     * @param id id của báo cáo
     * @return Optional chứa Report nếu tìm thấy, không có nếu không tìm thấy
     */
    @Override
    public Optional<Report> getReportById(Long id){
        return reportRepository.findById(id);
    }

    /**
     * Lấy danh sách tất cả báo cáo.
     * @return danh sách Report
     */
    @Override
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

    /**
     * Cập nhật báo cáo.
     * @param report Report cần cập nhật
     * @return Report đã được cập nhật
     */
    @Override
    public Report updateReport(Report report){
        return reportRepository.save(report);
    }

    /**
     * Xóa báo cáo theo id.
     * @param id id của báo cáo cần xóa
     */
    @Override
    public void deleteReport(Long id){
        reportRepository.deleteById(id);
    }
}
