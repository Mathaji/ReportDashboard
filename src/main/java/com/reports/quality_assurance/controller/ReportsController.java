package com.reports.quality_assurance.controller;

import com.reports.quality_assurance.dtos.Reports.AddReportsDTO;
import com.reports.quality_assurance.dtos.Reports.ReportsByNameDTO;
import com.reports.quality_assurance.dtos.Reports.AddedReportsDTO;
import com.reports.quality_assurance.model.ReportStatus;
import com.reports.quality_assurance.model.Reports;
import com.reports.quality_assurance.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle requests regarding the Reports
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@RestController
@RequestMapping("v1/report")
@RequiredArgsConstructor
public class ReportsController {

    private final ReportsService reportsService;

    @PostMapping("/add")
    public ResponseEntity<AddedReportsDTO> postReport(final @RequestBody AddReportsDTO reportData)
    {
        final AddedReportsDTO response;
        response = reportsService.addReport(reportData);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/allReports")
    public List<Reports> getAllReports()
    {
        final List<Reports> allReports;
        allReports = reportsService.getAllReports();

        return allReports;
    }

    @GetMapping("/name")
    public ReportsByNameDTO fetchReportsByName(final @RequestParam(name = "filename") String filename)
    {
        final ReportsByNameDTO reportsDTO;
        reportsDTO = reportsService.getReportsByName(filename);

        return reportsDTO;
    }

    @GetMapping("/date")
    public ResponseEntity<List<Reports>> fetchReportsByDate(final @RequestParam(name = "date") String dateUploaded)
    {
        final List<Reports> reportsByDate;
        reportsByDate = reportsService.getAllReportsByDate(dateUploaded);

        return ResponseEntity.ok(reportsByDate);
    }

    @GetMapping("/status")
    public List<Reports> fetchReportsByStatus(final @RequestParam(name = "status") ReportStatus status)
    {
        final List<Reports> foundReports;
        foundReports = reportsService.getAllReportsByStatus(status);

        return foundReports;
    }
}
