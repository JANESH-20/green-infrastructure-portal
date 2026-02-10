package com.greenportal.monitoting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataType;
    private String location;
    private int value;
    private String unit;
    private LocalDate date;
    private String notes;

    // Getters & Setters

}
