package com.greenportal.monitoting.entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // reports.location_id -> locations.id
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    // reports.report_type_id -> report_types.id
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @Setter
    @Column(name = "report_date")
    private LocalDate reportDate;

    @Setter
    @Column(name = "notes")
    private String notes;

    // report_values.report_id -> reports.id
    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportValue> reportValues = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public Location getLocation() { return location; }

    public ReportType getReportType() { return reportType; }

    public LocalDate getReportDate() { return reportDate; }

    public String getNotes() { return notes; }

    public List<ReportValue> getReportValues() { return reportValues; }
}
