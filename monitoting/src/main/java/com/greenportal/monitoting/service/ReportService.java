package com.greenportal.monitoting.service;

import com.greenportal.monitoting.entity.*;
import com.greenportal.monitoting.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final LocationRepository locationRepository;
    private final ReportTypeRepository reportTypeRepository;

    public ReportService(ReportRepository reportRepository,
                         LocationRepository locationRepository,
                         ReportTypeRepository reportTypeRepository) {
        this.reportRepository = reportRepository;
        this.locationRepository = locationRepository;
        this.reportTypeRepository = reportTypeRepository;
    }

    @Transactional
    public void saveReport(String dataType, String locationName, LocalDate date,
                           Double value, String unit) {

        Location location = locationRepository.findByName(locationName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid location: " + locationName));

        ReportType reportType = reportTypeRepository.findByName(dataType)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type: " + dataType));

        Report report = new Report();
        report.setLocation(location);
        report.setReportType(reportType);
        report.setReportDate(date);
        report.setNotes(null); // not using notes now

        ReportValue rv = new ReportValue();
        rv.setReport(report);
        rv.setReportType(reportType);
        rv.setValue(value);
        rv.setUnit(unit);

        report.getReportValues().add(rv);

        reportRepository.save(report);
    }
}
