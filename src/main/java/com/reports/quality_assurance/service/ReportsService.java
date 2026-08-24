package com.reports.quality_assurance.service;

import com.reports.quality_assurance.dtos.Reports.*;
import com.reports.quality_assurance.model.QualityAssuranceEngineers;
import com.reports.quality_assurance.model.ReportStatus;
import com.reports.quality_assurance.model.Reports;
import com.reports.quality_assurance.model.Squads;
import com.reports.quality_assurance.model.repository.QualityAssuranceEngineersRepository;
import com.reports.quality_assurance.model.repository.ReportsRepository;
import com.reports.quality_assurance.model.repository.SquadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Service class handling business logic for Reports
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Service
@RequiredArgsConstructor
public class ReportsService {

    /** Repository for managing <b>Reports</b> database operations */
    private final ReportsRepository reportsRepository;

    /** Repository for managing <b>Squads</b> database operations */
    private final SquadsRepository  squadsRepository;

    /** Repository for managing <b>QA Engineer</b> database operations */
    private final QualityAssuranceEngineersRepository engineersRepository;

    /**
     * Adds a new Report in the system.
     *
     * @param newReport a DTO containing the <b>new Report</b> details
     * @return the saved DTO representing the <b>added Report</b>
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li>If the <b>filename</b> is already in use.</li>
     *                                  <li>If the <b>Engineer</b> does not exist.</li>
     *                                  <li>If the <b>Squad</b> does not exist.</li>
     *                                  </ul>
     */
    @Transactional
    public ViewReportsDTO addReport(final AddReportsDTO newReport) {

        // Models
        final Reports report;
        final Squads  squad;
        final Squads  newSquad;
        final QualityAssuranceEngineers thisEngineer;
        final ViewReportsDTO viewAddedReportDetails;

        final Reports saveReport;

        // Variable placeholders for Incoming Request data
        final String filename;
        final String username;
        final String squadName;
        final ReportStatus status;
        final String pathToStoreFolder;

        // Initialize Model Objects
        report   = new Reports();
        newSquad = new Squads();

        // Assign values from
        username          = newReport.getUser();
        squadName         = newReport.getSquad();
        filename          = newReport.getFilename();
        pathToStoreFolder = newReport.getFolderPath();
        status            = newReport.getReportStatus();


        if(reportsRepository.findByFilename(filename).isPresent())
        {
            throw new IllegalArgumentException("Filename already exists");
        }

        else if(squadsRepository.findBySquadName(squadName).isEmpty())
        {
            newSquad.setSquadName(squadName);
            squadsRepository.save(newSquad);
        }


        thisEngineer = engineersRepository.findByUsername(username).orElseThrow(() ->  new IllegalArgumentException("Engineer not found: " + username));
        squad        = squadsRepository.findBySquadName(squadName).orElseThrow(() -> new IllegalArgumentException("Squad name not found: " + squadName));


        // Set the values
        {
            report.setUploadedBy(thisEngineer.getEngineerId());
            report.setSquadId(squad.getSquadId());
            report.setFilename(filename);
            report.setFolderPath(pathToStoreFolder);
            report.setReportStatus(status);
            saveReport = reportsRepository.save(report);
        }

        // Update Engineers Squads
        {
            if(!(thisEngineer.getSquads()).contains(squad))
            {
                thisEngineer.getSquads().add(squad);
                engineersRepository.save(thisEngineer);
            }
        }

        // Set DTO values
        final String engineerFullName;
        final String reportFilename;
        final String reportSquad;
        final ReportStatus reportStatus;
        final LocalDate    reportDate;

        engineerFullName = thisEngineer.getFirstName() + " " + thisEngineer.getLastName();
        reportFilename = saveReport.getFilename();
        reportSquad    = squadsRepository.findById(saveReport.getSquadId()).get().getSquadName();
        reportStatus   = saveReport.getReportStatus();
        reportDate     = saveReport.getDateUploaded();

        viewAddedReportDetails = new ViewReportsDTO(
                                    engineerFullName,
                                    reportFilename,
                                    reportSquad,
                                    reportStatus,
                                    reportDate
                                    );

        return viewAddedReportDetails;
    }

    /**
     * Retrieves all the Reports from the system
     *
     * @return a list containing all reports
     * @throws IllegalArgumentException if list is empty
     */
    @Transactional(readOnly = true)
    public List<ViewReportsDTO> getAllReports() {

        final List<Reports> allReports;
        allReports = reportsRepository.findAll();

        if (allReports.isEmpty()) {
            throw new IllegalArgumentException("No Reports found");
        }
        return allReports
                .stream()
                .map(Reports -> new ViewReportsDTO(engineersRepository.findById(Reports.getUploadedBy()).get().getFirstName() + " " + engineersRepository.findById(Reports.getUploadedBy()).get().getLastName(),
                        Reports.getFilename(),
                        squadsRepository.findById(Reports.getSquadId()).get().getSquadName(),
                        Reports.getReportStatus(),
                        Reports.getDateUploaded()))
                .toList();
    }

    /**
     * Retrieves a report by referencing the report name
     *
     * @param filename a String value of the filename
     * @return a DTO representing the searched report
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li>If the <b>Report</b> <i>(By that specific filename)</i> does not exist</li>
     *                                  <li>If the <b>Squad</b> does not exist</li>
     *                                  <li>If the <b>Engineer</b> does not exist</li>
     *                                  </ul>
     */
    @Transactional(readOnly = true)
    public ViewReportsDTO getReportsByName(final String filename) {

        final Reports createdReports;
        final Squads userSquad;
        final QualityAssuranceEngineers engineer;
        final String engineerFullName;
        final ViewReportsDTO reportByName;


        createdReports = reportsRepository.findByFilename(filename).orElseThrow(() -> new IllegalArgumentException("Report does not exist"));
        userSquad      = squadsRepository.findById(createdReports.getSquadId()).orElseThrow(() -> new IllegalArgumentException("Squad does not exist"));
        engineer       = engineersRepository.findById(createdReports.getUploadedBy()).orElseThrow(() -> new IllegalArgumentException("Engineer not found"));
        engineerFullName = engineer.getFirstName() + " " + engineer.getLastName();

        reportByName = new ViewReportsDTO(
                engineerFullName,
                createdReports.getFilename(),
                userSquad.getSquadName(),
                createdReports.getReportStatus(),
                createdReports.getDateUploaded());

        return reportByName;
    }

    /**
     * Retrieves a list of Reports based on their upload date
     *
     * @param dateUploaded the date on which the report was uploaded (e.g., {@code "11 August 2026"})
     * @return a list containing reports uploaded on the searched date
     * @throws IllegalArgumentException if list is empty or null
     */
    @Transactional(readOnly = true)
    public List<ViewReportsDTO> getAllReportsByDate(final String dateUploaded) {
        final LocalDate         formattedDate;
        final DateTimeFormatter formatStringDate;
        final List<Reports>     foundReports;

        // Convert the String date format into a LocalDate Format
        formatStringDate = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        formattedDate    = LocalDate.parse(dateUploaded.strip(), formatStringDate);

        // Search for the Report
        foundReports = reportsRepository.findByDateUploaded(formattedDate);

        if(foundReports == null || foundReports.isEmpty())
        {
            throw new IllegalArgumentException(String.format("No Reports uploaded on date: '%s' found", dateUploaded));
        }

        return foundReports
                .stream()
                .map(Reports -> new ViewReportsDTO(engineersRepository.findById(Reports.getUploadedBy()).get().getFirstName() + " " + engineersRepository.findById(Reports.getUploadedBy()).get().getLastName(),
                        Reports.getFilename(),
                        squadsRepository.findById(Reports.getSquadId()).get().getSquadName(),
                        Reports.getReportStatus(),
                        Reports.getDateUploaded()))
                .toList();
    }

    /**
     * Retrieves a list of Reports based on their upload status
     *
     * @param status the status given to the report when it was uploaded (e.g., {@code "PASSED", "FAILED",  "BROKEN" OR "INCOMPLETE"})
     * @return a list containing reports uploaded with the searched status
     * @throws IllegalArgumentException if list is empty or null
     */
    @Transactional(readOnly = true)
    public List<ViewReportsDTO> getAllReportsByStatus(final ReportStatus status)
    {
        final List<Reports> foundReports;
        foundReports = reportsRepository.findByReportStatus(status);

        if (foundReports == null || foundReports.isEmpty()) {
            throw new IllegalArgumentException(String.format("No Reports with status: %s found", status));
        }

        return foundReports
                .stream()
                .map(Reports -> new ViewReportsDTO(engineersRepository.findById(Reports.getUploadedBy()).get().getFirstName() + " " + engineersRepository.findById(Reports.getUploadedBy()).get().getLastName(),
                        Reports.getFilename(),
                        squadsRepository.findById(Reports.getSquadId()).get().getSquadName(),
                        Reports.getReportStatus(),
                        Reports.getDateUploaded()))
                .toList();
    }

    /**
     * Deletes a report from the database matching the provided filename
     *
     * @param filename the name of the Report to be deleted from the database
     * @return a DTO confirming that the Report has been deleted
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                      <li>If the filename is null or empty</li>
     *                                      <li>If the filename does not exist</li>
     *                                  </ul>
     */
    @Transactional
    public DeletedReportsDTO deleteReportsByFilename(final String filename) {

        final DeletedReportsDTO deletedReportMessage;
        final Reports report;
        final String message;

        if (filename == null || filename.isEmpty())
        {
            throw new IllegalArgumentException("No filename was specified to delete");
        }

        if (reportsRepository.findByFilename(filename).isEmpty()) {
            throw new IllegalArgumentException(String.format("Report: %s does not exist", filename));
        }

        report = reportsRepository.findByFilename(filename).get();
        message = String.format("Report: '%s' deleted Successfully", report.getFilename());

        deletedReportMessage = new DeletedReportsDTO(report.getFilename(),
                                                     message);

        reportsRepository.deleteByFilename(filename);
        return deletedReportMessage;
    }
}