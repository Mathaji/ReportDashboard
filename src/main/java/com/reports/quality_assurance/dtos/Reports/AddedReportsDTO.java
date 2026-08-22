package com.reports.quality_assurance.dtos.Reports;

import com.reports.quality_assurance.model.ReportStatus;

/**
 * DTO class handling outgoing requests for newly created reports.
 *
 * @param filename the name of the report successfully created
 * @param squadName the squad the report belongs to
 * @param status the status of the report
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

public record AddedReportsDTO (
        String filename,
        String squadName,
        ReportStatus status )
{}
