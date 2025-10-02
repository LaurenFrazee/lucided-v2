package com.lauren.lucided.service;

import com.lauren.lucided.model.Report;
import com.lauren.lucided.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    public Report updateReport(Long id, Report updatedReport) {
        return reportRepository.findById(id)
                .map(existing -> {
                    existing.setSummary(updatedReport.getSummary());
                    existing.setScore(updatedReport.getScore());
                    existing.setStudent(updatedReport.getStudent());
                    return reportRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }
}