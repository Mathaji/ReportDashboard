package com.reports.quality_assurance.dtos.Reports;

/**
 * DTO class handling outgoing requests for deleted reports
 *
 * @param filename the name of the Report that was deleted
 * @param message a message describing the action that was made
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */
public record DeletedReportsDTO(
        String filename,
        String message
)
{}
