package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add-data")
    public String addDataPage(Model model) {
        model.addAttribute("pageTitle", "Add Data - Green Portal");
        model.addAttribute("activePage", "add");
        model.addAttribute("headerTitle", "Add Data");
        model.addAttribute("subTitle", "Quick admin entry");
        return "add_data";   // ✅ must match templates/add_data.html
    }


    @PostMapping("/saveReport")
    public String saveReport(@RequestParam String dataType,
                             @RequestParam String location,
                             @RequestParam String date,
                             @RequestParam Double value,
                             @RequestParam String unit,
                             @RequestParam(defaultValue = "false") boolean saveAndNew,
                             Model model) {

        reportService.saveReport(dataType, location, LocalDate.parse(date), value, unit);

        if (saveAndNew) {
            model.addAttribute("success", "Saved successfully. Add next entry.");
            return "add-data";
        }

        return "redirect:/reports/weekly";
    }
}
