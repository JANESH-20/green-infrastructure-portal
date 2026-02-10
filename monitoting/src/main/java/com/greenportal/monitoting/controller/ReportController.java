package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.entity.Report;
import com.greenportal.monitoting.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/saveReport")
    public String saveReport(@ModelAttribute Report report) {
        service.saveReport(report);
        return "redirect:/";
    }

    @GetMapping("/viewReports")
    public String viewReports(Model model) {
        model.addAttribute("reports", service.getAllReports());
        return "viewReports";
    }
}
