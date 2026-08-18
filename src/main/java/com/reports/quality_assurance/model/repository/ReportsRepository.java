package com.reports.quality_assurance.model.repository;

import com.reports.quality_assurance.model.ReportStatus;
import com.reports.quality_assurance.model.Reports;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing interactions with {@link Reports}
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {

    /**
     * Retrieves a Report by the unique <b>filename</b>
     *
     * @param filename the name given to the report when uploaded
     * @return the report corresponding to the unique <b>filename</b>
     */
    Optional<Reports> findByFilename(final String filename);

    /**
     * Retrieves a list of Reports ordered by the date which they were uploaded
     *
     * @param dateUploaded the date which the report was uploaded
     * @return a list of all uploaded reports ordered by the date they were uploaded
     */
    List<Reports> findByDateUploaded(final LocalDate dateUploaded);

    /**
     * Retrieves a list of Reports filtered by status
     *
     * @param reportStatus the status of the report when it was uploaded {@link ReportStatus}
     * @return a list of all the reports with the same queried status
     */
    List<Reports> findByReportStatus(final ReportStatus reportStatus);
}
