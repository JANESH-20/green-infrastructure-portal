package com.greenportal.monitoting.service;

import com.greenportal.monitoting.repository.ReportValueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {

    private final ReportValueRepository repo;

    public DashboardService(ReportValueRepository repo) {
        this.repo = repo;
    }

    public double totalTrees() {
        return repo.getTodayTotalByType("Trees Planted",LocalDate.now());
    }

    public double waterUsedToday() {
        return repo.getTodayTotalByType("Water Usage", LocalDate.now());
    }

    public double electricityToday() {
        return repo.getTodayTotalByType("Electricity Consumption", LocalDate.now());
    }

    public double wasteToday() {
        return repo.getTodayTotalByType("Waste Collected", LocalDate.now());
    }
}
