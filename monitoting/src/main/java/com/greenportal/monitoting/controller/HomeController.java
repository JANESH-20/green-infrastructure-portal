package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;

@Controller
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String dashboard(@RequestParam(value = "week", required = false) String week,
                            Model model) {

        LocalDate start;

        // If week not provided => current week (Monday)
        if (week == null || week.isBlank()) {
            start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        } else {
            // week format: "2026-W09"
            int year = Integer.parseInt(week.substring(0, 4));
            int weekNumber = Integer.parseInt(week.substring(6));

            // ISO week: Use Jan 4th as anchor, then set week and Monday
            start = LocalDate.of(year, 1, 4)
                    .with(WeekFields.ISO.weekOfWeekBasedYear(), weekNumber)
                    .with(WeekFields.ISO.dayOfWeek(), 1); // Monday
        }

        LocalDate end = start.plusDays(6);

        // For input type="week" -> expects YYYY-Www
        String selectedWeek = start.format(DateTimeFormatter.ofPattern("YYYY-'W'ww"));

        model.addAttribute("weekStart", start);
        model.addAttribute("weekEnd", end);
        model.addAttribute("selectedWeek", selectedWeek);

        model.addAttribute("waterWeek", dashboardService.weekTotalByType("Water Usage", start, end));
        model.addAttribute("electricityWeek", dashboardService.weekTotalByType("Electricity Consumption", start, end));
        model.addAttribute("wasteWeek", dashboardService.weekTotalByType("Waste Collected", start, end));
        model.addAttribute("totalTrees", dashboardService.totalTreesAllTime());

        // Chart arrays (for selected week)
        model.addAttribute("locationLabels", dashboardService.locationLabels());
        model.addAttribute("waterByLocation", dashboardService.weekTotalsByLocation("Water Usage", start, end));
        model.addAttribute("electricityByLocation", dashboardService.weekTotalsByLocation("Electricity Consumption", start, end));
        model.addAttribute("wasteByLocation", dashboardService.weekTotalsByLocation("Waste Collected", start, end));
        model.addAttribute("treesByLocation", dashboardService.weekTotalsByLocation("Trees Planted", start, end));

        // Recent rows (last 8)
        model.addAttribute("recentRows", dashboardService.recentRows(8));

        return "dashboard_admin";
    }
}