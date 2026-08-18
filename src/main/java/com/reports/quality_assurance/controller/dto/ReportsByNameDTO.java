package com.reports.quality_assurance.controller.dto;


import java.time.LocalDate;

/**
 * DTO class handling outgoing requests for Reports searched by name
 *
 * @param engineer the Quality Assurance Engineer's <b>First name</b> and <b>Last Name</b>
 * @param filename the filename of the searched report <i>(Name given to the report when it was saved)</i>
 * @param folderPath the absolute path pointing to the <b><i>index.html</i></b> of the saved report
 * @param dateUploaded the date at which the file was uploaded
 * @param squad the squad that the report belongs to
 * @param status the status of the report
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

public record ReportsByNameDTO(
        String engineer,
        String filename,
        String folderPath,
        LocalDate dateUploaded,
        String squad,
        String status
)
{
}
