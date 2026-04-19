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

    // CHANGE THIS: Match the form action in your HTML
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "selectedWeek", required = false) String week,
                            @RequestParam(value = "metric", defaultValue = "water") String metric,
                            Model model) {
        LocalDate start;
        try {
            if (week == null || week.isBlank()) {
                start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            } else {
                String[] parts = week.split("-W");
                int year = Integer.parseInt(parts[0]);
                int weekNum = Integer.parseInt(parts[1]);

                start = LocalDate.of(year, 1, 4)
                        .with(WeekFields.ISO.weekOfWeekBasedYear(), weekNum)
                        .with(WeekFields.ISO.dayOfWeek(), 1);
            }
        } catch (Exception e) {
            start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        }

        LocalDate end = start.plusDays(6);
        // Use lowercase 'w' for Thymeleaf compatibility with HTML5 input
        String currentWeekFormatted = start.format(DateTimeFormatter.ofPattern("yyyy-'W'ww"));

        model.addAttribute("weekStart", start);
        model.addAttribute("weekEnd", end);
        model.addAttribute("selectedWeek", currentWeekFormatted);
        model.addAttribute("currentMetric", metric);

        // Data fetching
        model.addAttribute("waterWeek", dashboardService.weekTotalByType("Water Usage", start, end));
        model.addAttribute("electricityWeek", dashboardService.weekTotalByType("Electricity Consumption", start, end));
        model.addAttribute("wasteWeek", dashboardService.weekTotalByType("Waste Collected", start, end));
        model.addAttribute("totalTrees", dashboardService.totalTreesAllTime());

        model.addAttribute("locationLabels", dashboardService.locationLabels());
        model.addAttribute("waterByLocation", dashboardService.weekTotalsByLocation("Water Usage", start, end));
        model.addAttribute("electricityByLocation", dashboardService.weekTotalsByLocation("Electricity Consumption", start, end));
        model.addAttribute("wasteByLocation", dashboardService.weekTotalsByLocation("Waste Collected", start, end));
        model.addAttribute("treesByLocation", dashboardService.weekTotalsByLocation("Trees Planted", start, end));

        model.addAttribute("recentRows", dashboardService.recentRows(8));
        model.addAttribute("insights", dashboardService.generateInsights(start, end));
        model.addAttribute("alerts", dashboardService.generateAlerts(start, end));

        return "dashboard_admin";
    }

    // Keep this for the root URL
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }
}