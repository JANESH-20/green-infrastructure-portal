package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.ReportValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReportValueRepository extends JpaRepository<ReportValue, Long> {

    @Query("""
    select coalesce(sum(rv.value), 0)
    from ReportValue rv
    join rv.report r
    join rv.reportType rt
    where rt.name = :typeName
      and r.reportDate between :startDate and :endDate
""")
    Double sumByTypeBetweenDates(@Param("typeName") String typeName,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

}
