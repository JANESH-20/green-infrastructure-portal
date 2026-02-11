package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.entity.Report;
import com.greenportal.monitoting.repository.ReportRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final ReportRepository reportRepository;

    public HomeController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Dashboard
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalTrees", 120);
        model.addAttribute("waterUsedToday", "2026-02-03 09:00");
        model.addAttribute("electricityConsumption", 8);
        model.addAttribute("wasteCollected", 2);

        return "dashboard_admin";
    }

    // Show Add Report form
    @GetMapping("/add-data")
    public String addData(Model model) {
        model.addAttribute("report", new Report()); // bind empty Report for form
        return "add_data";
    }
    // Show all reports
    @GetMapping("/view-reports")
    public String viewReports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        return "view_reports";  // Thymeleaf page
    }

}
