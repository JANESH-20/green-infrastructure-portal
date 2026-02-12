package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.ReportValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReportValueRepository extends JpaRepository<ReportValue, Long> {

    // TODAY total by type (water, electricity, waste, Tree)
    @Query("""
        SELECT COALESCE(SUM(rv.value), 0)
        FROM ReportValue rv
        WHERE rv.reportType.name = :type
          AND rv.report.reportDate = :date
    """)
    double getTodayTotalByType(@Param("type") String type,
                               @Param("date") LocalDate date);
}
