package com.greenportal.monitoting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_values")
public class ReportValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // report_values.report_id -> reports.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    // report_values.report_type_id -> report_types.id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_type_id")
    private ReportType reportType;

    @Column(name = "value")
    private Double value;

    @Column(name = "unit")
    private String unit;

    // getters/setters
    public Long getId() { return id; }
    public Report getReport() { return report; }
    public void setReport(Report report) { this.report = report; }
    public ReportType getReportType() { return reportType; }
    public void setReportType(ReportType reportType) { this.reportType = reportType; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
