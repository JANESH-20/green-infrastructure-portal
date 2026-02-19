package com.greenportal.monitoting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "report_types")
public class ReportType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
