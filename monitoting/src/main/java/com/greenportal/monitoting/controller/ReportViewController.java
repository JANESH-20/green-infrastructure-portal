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
    public String viewReports(@RequestParam(value = "week", required = false) String week,
                              Model model) {

        LocalDate weekStart;

        if (week == null || week.isBlank()) {
            LocalDate today = LocalDate.now();
            weekStart = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else {
            int year = Integer.parseInt(week.substring(0, 4));
            int weekNumber = Integer.parseInt(week.substring(6));

            weekStart = LocalDate.of(year, 1, 4) // ISO week anchor
                    .with(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear(), weekNumber)
                    .with(java.time.temporal.WeekFields.ISO.dayOfWeek(), 1); // Monday
        }

        LocalDate weekEnd = weekStart.plusDays(6);

        model.addAttribute("weekStart", weekStart);
        model.addAttribute("weekEnd", weekEnd);
        model.addAttribute("selectedWeek",
                weekStart.format(java.time.format.DateTimeFormatter.ofPattern("YYYY-'W'ww")));

        model.addAttribute("reports", reportRepository.findWeeklyWithValues(weekStart, weekEnd));

        // ✅ for dynamic dropdown + blocks
        model.addAttribute("locations", locationRepository.findAll());

        return "viewReports";
    }
}
