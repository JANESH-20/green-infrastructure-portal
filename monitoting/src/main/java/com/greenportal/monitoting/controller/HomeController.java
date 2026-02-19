package com.greenportal.monitoting.controller;

import com.greenportal.monitoting.service.DashboardService;
import com.greenportal.monitoting.service.DashboardService.RecentRow;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        LocalDate start = dashboardService.weekStart(LocalDate.now());
        LocalDate end = start.plusDays(6);

        model.addAttribute("weekStart", start);
        model.addAttribute("weekEnd", end);

        model.addAttribute("waterWeek", dashboardService.weekTotalByType("Water Usage", start, end));
        model.addAttribute("electricityWeek", dashboardService.weekTotalByType("Electricity Consumption", start, end));
        model.addAttribute("wasteWeek", dashboardService.weekTotalByType("Waste Collected", start, end));
        model.addAttribute("totalTrees", dashboardService.totalTreesAllTime());

        // Chart arrays
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
