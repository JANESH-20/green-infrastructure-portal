package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.entity.Report;
import com.greenportal.monitoting.repository.ReportRepository;
import com.greenportal.monitoting.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ReportRepository reportRepository;
    private final DashboardService dashboardService;

    public HomeController(ReportRepository reportRepository,
                          DashboardService dashboardService) {
        this.reportRepository = reportRepository;
        this.dashboardService = dashboardService;
    }

    // Dashboard
    @GetMapping("/")
    public String dashboard(Model model) {

        model.addAttribute("totalTrees", dashboardService.totalTrees());
        model.addAttribute("waterUsedToday",
                dashboardService.waterUsedToday() + " L");
        model.addAttribute("electricityConsumption",
                dashboardService.electricityToday() + " kWh");
        model.addAttribute("wasteCollected",
                dashboardService.wasteToday() + " kg");

        return "dashboard_admin";
    }

    // Show Add Report form
    @GetMapping("/add-data")
    public String addData(Model model) {
        model.addAttribute("report", new Report());
        return "add_data";
    }

    // Show all reports
    @GetMapping("/view-reports")
    public String viewReports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        return "viewReports";
    }
}
