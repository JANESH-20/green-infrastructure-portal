package com.greenportal.monitoting.service;

import com.greenportal.monitoting.entity.*;
import com.greenportal.monitoting.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReportService {

    private final LocationRepository locationRepo;
    private final ReportTypeRepository typeRepo;
    private final ReportRepository reportRepo;
    private final ReportValueRepository valueRepo;

    public ReportService(LocationRepository locationRepo,
                         ReportTypeRepository typeRepo,
                         ReportRepository reportRepo,
                         ReportValueRepository valueRepo) {
        this.locationRepo = locationRepo;
        this.typeRepo = typeRepo;
        this.reportRepo = reportRepo;
        this.valueRepo = valueRepo;
    }

    @Transactional
    public void saveReport(String dataType,
                           String locationName,
                           double value,
                           String unit,
                           LocalDate date,
                           String notes) {

        // 1️⃣ Get or create Location
        Location location = locationRepo.findByName(locationName)
                .orElseGet(() -> {
                    Location l = new Location();
                    l.setName(locationName);
                    return locationRepo.save(l);
                });

        // 2️⃣ Get or create ReportType
        ReportType type = typeRepo.findByName(dataType)
                .orElseGet(() -> {
                    ReportType t = new ReportType();
                    t.setName(dataType);
                    return typeRepo.save(t);
                });

        // 3️⃣ Save Report
        Report report = new Report();
        report.setLocation(location);
        report.setReportDate(date);
        report.setNotes(notes);
        report.setReportType(type);
        report = reportRepo.save(report);

        // 4️⃣ Save ReportValue
        ReportValue rv = new ReportValue();
        rv.setReport(report);
        rv.setReportType(type);
        rv.setValue(value);
        rv.setUnit(unit);

        valueRepo.save(rv);
    }
}
