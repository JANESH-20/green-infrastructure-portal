package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping("/saveReport")
    public String saveReport(@RequestParam String dataType,
                             @RequestParam String location,
                             @RequestParam double value,
                             @RequestParam String unit,
                             @RequestParam String date,
                             @RequestParam String notes,
                             RedirectAttributes ra) {

        service.saveReport(
                dataType,
                location,
                value,
                unit,
                LocalDate.parse(date),
                notes
        );

        ra.addFlashAttribute("success", "Data saved successfully!");

        return "redirect:/add-data";
    }
}
