package com.greenportal.monitoting.service;

import com.greenportal.monitoting.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    private final ReportRepository reportRepository;

    // Keep location names in the same order everywhere
    private static final List<String> LOCATIONS = List.of(
            "Main Campus",
            "Admin Block",
            "Hostel",
            "Campus Garden"
    );

    public DashboardService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public LocalDate weekStart(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public Double weekTotalByType(String typeName, LocalDate start, LocalDate end) {
        Double sum = reportRepository.sumValueByTypeAndDateRange(typeName, start, end);
        return sum == null ? 0.0 : sum;
    }

    public Double totalTreesAllTime() {
        Double sum = reportRepository.sumValueByTypeAllTime("Trees Planted");
        return sum == null ? 0.0 : sum;
    }

    public List<String> locationLabels() {
        return LOCATIONS;
    }

    public List<Double> weekTotalsByLocation(String typeName, LocalDate start, LocalDate end) {
        // Query returns (locationName, sum)
        var rows = reportRepository.sumValueByTypeGroupedByLocation(typeName, start, end);

        // Fill defaults as 0 for missing locations
        List<Double> result = new ArrayList<>();
        for (String loc : LOCATIONS) {
            Double value = 0.0;
            for (var r : rows) {
                if (loc.equals(r.getLocationName())) {
                    value = (r.getTotal() == null) ? 0.0 : r.getTotal();
                    break;
                }
            }
            result.add(value);
        }
        return result;
    }

    public record RecentRow(LocalDate reportDate, String locationName, String typeName, Double value, String unit) {}

    public List<RecentRow> recentRows(int limit) {
        var rows = reportRepository.findRecentRows(limit);
        List<RecentRow> out = new ArrayList<>();
        for (var r : rows) {
            out.add(new RecentRow(
                    r.getReportDate(),
                    r.getLocationName(),
                    r.getTypeName(),
                    r.getValue() == null ? 0.0 : r.getValue(),
                    r.getUnit() == null ? "" : r.getUnit()
            ));
        }
        return out;
    }
}
