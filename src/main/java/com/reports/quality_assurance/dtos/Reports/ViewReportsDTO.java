package com.reports.quality_assurance.dtos.Reports;

import com.reports.quality_assurance.model.ReportStatus;

import java.time.LocalDate;

/**
 * DTO class handling outgoing requests to view report data.
 *
 * @param engineer the name of the Quality Assurance Engineer the report was created by
 * @param filename the name of the report successfully created
 * @param squadName the squad the report belongs to
 * @param status the status of the report
 * @param dateUploaded the date which the report was uploaded
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */
public record ViewReportsDTO(
        String engineer,
        String filename,
        String squadName,
        ReportStatus status,
        LocalDate dateUploaded
)
{}
