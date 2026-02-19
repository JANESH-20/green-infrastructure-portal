package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.entity.*;
import com.greenportal.monitoting.repository.*;
import com.greenportal.monitoting.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Controller
public class ReportViewController {

    private final DashboardService dashboardService;
    private final ReportRepository reportRepository;
    private final LocationRepository locationRepository;
    private final ReportTypeRepository reportTypeRepository;

    public ReportViewController(DashboardService dashboardService,
                                ReportRepository reportRepository,
                                LocationRepository locationRepository,
                                ReportTypeRepository reportTypeRepository) {
        this.dashboardService = dashboardService;
        this.reportRepository = reportRepository;
        this.locationRepository = locationRepository;
        this.reportTypeRepository = reportTypeRepository;
    }

    // ===================== VIEW REPORTS (WEEKLY) =====================
    @GetMapping("/view-reports")
    public String viewReports(Model model) {

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);

        model.addAttribute("weekStart", weekStart);
        model.addAttribute("weekEnd", weekEnd);

        // IMPORTANT: use a fetch query so thymeleaf can read reportValues without Lazy errors
        model.addAttribute("reports", reportRepository.findWeeklyWithValues(weekStart, weekEnd));

        return "viewReports";
    }
}
