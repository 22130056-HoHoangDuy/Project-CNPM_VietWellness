// ReportService.java
package com.vietwellness.community.service;

import com.vietwellness.community.entity.Report;
import java.util.List;
import java.util.Optional;

public interface ReportService {
    Report createReport(Report report);
    Optional<Report> getReportById(Long id);
    List<Report> getAllReports();
    Report updateReport(Report report);
    void deleteReport(Long id);
}
