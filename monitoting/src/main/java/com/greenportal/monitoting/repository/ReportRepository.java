package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
