package com.reports.quality_assurance.controller;

import com.reports.quality_assurance.dtos.Reports.AddReportsDTO;
import com.reports.quality_assurance.dtos.Reports.ViewReportsDTO;
import com.reports.quality_assurance.dtos.Reports.DeletedReportsDTO;
import com.reports.quality_assurance.model.ReportStatus;
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
    public ResponseEntity<ViewReportsDTO> postReport(final @RequestBody AddReportsDTO reportData)
    {
        final ViewReportsDTO response;
        response = reportsService.addReport(reportData);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/allReports")
    public ResponseEntity<List<ViewReportsDTO>> getAllReports() {
        final List<ViewReportsDTO> allReports;
        allReports = reportsService.getAllReports();

        return ResponseEntity.ok(allReports);
    }

    @GetMapping("/name")
    public ResponseEntity<ViewReportsDTO> fetchReportsByName(final @RequestParam(name = "filename") String filename)
    {
        final ViewReportsDTO reportsDTO;
        reportsDTO = reportsService.getReportsByName(filename);

        return ResponseEntity.ok(reportsDTO);
    }

    @GetMapping("/date")
    public ResponseEntity<List<ViewReportsDTO>> fetchReportsByDate(final @RequestParam(name = "date") String dateUploaded)
    {
        final List<ViewReportsDTO> reportsByDate;
        reportsByDate = reportsService.getAllReportsByDate(dateUploaded);

        return ResponseEntity.ok(reportsByDate);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ViewReportsDTO>> fetchReportsByStatus(final @RequestParam(name = "status") ReportStatus status)
    {
        final List<ViewReportsDTO> foundReports;
        foundReports = reportsService.getAllReportsByStatus(status);

        return ResponseEntity.ok(foundReports);
    }

    @DeleteMapping("/deleteReport")
    public ResponseEntity<DeletedReportsDTO> deleteReportByFilename(final @RequestParam(name = "filename") String filename)
    {
        final DeletedReportsDTO deletedReport;
        deletedReport = reportsService.deleteReportsByFilename(filename);

        return ResponseEntity.ok(deletedReport);
    }
}
