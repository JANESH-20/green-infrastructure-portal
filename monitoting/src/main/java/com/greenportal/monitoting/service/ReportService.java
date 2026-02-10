package com.greenportal.monitoting.service;

import com.greenportal.monitoting.entity.Report;
import com.greenportal.monitoting.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository repo;

    public ReportService(ReportRepository repo) {
        this.repo = repo;
    }

    public void saveReport(Report report) {
        repo.save(report);
    }

    public List<Report> getAllReports() {
        return repo.findAll();
    }
}
