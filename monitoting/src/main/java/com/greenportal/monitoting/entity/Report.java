package com.greenportal.monitoting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reports")
@Getter
@Setter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "report_date")
    private LocalDate reportDate;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "report_type_id", nullable = false)
    private ReportType reportType;

}
