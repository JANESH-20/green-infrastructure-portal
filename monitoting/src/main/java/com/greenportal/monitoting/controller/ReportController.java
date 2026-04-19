package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @GetMapping("/add-data")
    public String addDataPage(org.springframework.ui.Model model) {

        model.addAttribute("pageTitle", "Add Data - Green Portal");
        model.addAttribute("activePage", "add");
        model.addAttribute("headerTitle", "Add Data");
        model.addAttribute("subTitle", "Quick admin entry");

        return "add_data";
    }

    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    @PostMapping("/saveReport")
    public String saveReport(@RequestParam String dataType,
                             @RequestParam String location,
                             @RequestParam String date,
                             @RequestParam Double value,
                             @RequestParam String unit,
                             @RequestParam(defaultValue = "false") boolean saveAndNew,
                             RedirectAttributes ra) {

        reportService.saveReport(dataType, location, LocalDate.parse(date), value, unit);

        if (saveAndNew) {
            ra.addFlashAttribute("success", "Saved successfully. Add next entry.");
            return "redirect:/add-data";
        }

        ra.addFlashAttribute("success", "Saved successfully.");
        return "redirect:/add-data";
    }
}
