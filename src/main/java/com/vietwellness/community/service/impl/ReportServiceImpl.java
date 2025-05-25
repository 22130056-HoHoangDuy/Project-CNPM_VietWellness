// ReportServiceImpl.java
package com.vietwellness.community.service.impl;

import com.vietwellness.community.entity.Report;
import com.vietwellness.community.repository.ReportRepository;
import com.vietwellness.community.service.ReportService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository){
        this.reportRepository = reportRepository;
    }

    @Override
    public Report createReport(Report report){
        return reportRepository.save(report);
    }

    @Override
    public Optional<Report> getReportById(Long id){
        return reportRepository.findById(id);
    }

    @Override
    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }

    @Override
    public Report updateReport(Report report){
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(Long id){
        reportRepository.deleteById(id);
    }
}
