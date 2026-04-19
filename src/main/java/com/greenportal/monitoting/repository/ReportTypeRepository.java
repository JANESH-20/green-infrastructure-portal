package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportTypeRepository extends JpaRepository<ReportType, Long> {
    Optional<ReportType> findByName(String name);
}
