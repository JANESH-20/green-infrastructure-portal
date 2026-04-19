package com.greenportal.monitoting.repository;

import com.greenportal.monitoting.entity.Report;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // --- 1) Weekly total for a type ---
    @Query("""
        SELECT SUM(rv.value)
        FROM ReportValue rv
        JOIN rv.report r
        JOIN rv.reportType rt
        WHERE rt.name = :typeName
          AND r.reportDate BETWEEN :start AND :end
    """)
    Double sumValueByTypeAndDateRange(String typeName, LocalDate start, LocalDate end);

    // --- 2) All time total for a type (Trees) ---
    @Query("""
        SELECT SUM(rv.value)
        FROM ReportValue rv
        JOIN rv.report r
        JOIN rv.reportType rt
        WHERE rt.name = :typeName
    """)
    Double sumValueByTypeAllTime(String typeName);

    // --- 3) Totals by location for chart ---
    interface LocationTotalRow {
        String getLocationName();
        Double getTotal();
    }

    @Query("""
        SELECT l.name AS locationName, SUM(rv.value) AS total
        FROM ReportValue rv
        JOIN rv.report r
        JOIN r.location l
        JOIN rv.reportType rt
        WHERE rt.name = :typeName
          AND r.reportDate BETWEEN :start AND :end
        GROUP BY l.name
    """)
    List<LocationTotalRow> sumValueByTypeGroupedByLocation(String typeName, LocalDate start, LocalDate end);

    // --- 4) Recent rows (for mini table) ---
    interface RecentRowProjection {
        LocalDate getReportDate();
        String getLocationName();
        String getTypeName();
        Double getValue();
        String getUnit();
    }

    @Query("""
        SELECT r.reportDate AS reportDate,
               l.name AS locationName,
               rt.name AS typeName,
               rv.value AS value,
               rv.unit AS unit
        FROM ReportValue rv
        JOIN rv.report r
        JOIN r.location l
        JOIN rv.reportType rt
        ORDER BY r.reportDate DESC, r.id DESC, rv.id DESC
    """)
    List<RecentRowProjection> findRecentRows(Pageable pageable);

    default List<RecentRowProjection> findRecentRows(int limit) {
        return findRecentRows(Pageable.ofSize(limit));
    }

    @Query("""
    SELECT DISTINCT r
    FROM Report r
    LEFT JOIN FETCH r.reportValues rv
    WHERE r.reportDate BETWEEN :start AND :end
    ORDER BY r.reportDate DESC
""")
    List<Report> findWeeklyWithValues(LocalDate start, LocalDate end);

}
