package com.reports.quality_assurance.repository;

import com.reports.quality_assurance.model.QualityAssuranceEngineers;
import com.reports.quality_assurance.model.ReportStatus;
import com.reports.quality_assurance.model.Reports;
import com.reports.quality_assurance.model.Squads;
import com.reports.quality_assurance.model.repository.QualityAssuranceEngineersRepository;
import com.reports.quality_assurance.model.repository.ReportsRepository;
import com.reports.quality_assurance.model.repository.SquadsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for {@link ReportsRepository} to validate standard <b>CRUD operations</b> and <b>Custom Query boundaries</b>:
 * <ul>
 *     <li>{@code save()} method to upload new Reports to the database</li>
 *     <li>{@code findAll()} method to find all uploaded Reports in the database</li>
 *     <li>{@code findByFilename(String filename)} method to find Reports in the database by <b>their filename</b></li>
 *     <li>{@code findByReportStatus(ReportStatus reportStatus} method to find Reports in the database by <b>their status</b></li>
 *     <li>{@code findByDateUploaded(LocalDate dateUploaded} method to find Reports in the database by <b>the date they were uploaded</b></li>
 *     <li>{@code deleteByFilename(String filename)} method to delete Reports in the database by <b>their filename</b></li>
 * </ul>
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReportsRepositoryTest {

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private QualityAssuranceEngineersRepository engineersRepository;

    @Autowired
    private SquadsRepository squadsRepository;


    private Reports passedReport;
    private Reports failedReport;
    private Reports incompleteReport;
    private Reports brokenReport;

    private static final String ROOT_PATH_DIRECTORY = "C:\\User\\Reports";

    @BeforeEach
    void setUp() {
        final QualityAssuranceEngineers engineer1;
        final QualityAssuranceEngineers engineer2;
        final Squads bmpSquad;
        final Squads bigDataSquad;
        final Squads qaEnablementSquad;
        final Squads contextualSquad;

        // Arrange Engineers
        engineer1 = QualityAssuranceEngineers.builder()
                .firstName("Karabo")
                .lastName("Phoshoko")
                .username("phosk002")
                .build();

        engineer2 = QualityAssuranceEngineers.builder()
                .firstName("Nehal")
                .lastName("Mohanlal")
                .username("mohan002")
                .build();

        // Arrange Squads
        {
            bmpSquad = Squads.builder()
                    .squadName("BMP").build();

            bigDataSquad = Squads.builder()
                    .squadName("Big Data").build();

            qaEnablementSquad = Squads.builder()
                    .squadName("Enablement").build();

            contextualSquad = Squads.builder()
                    .squadName("Contextual").build();
        }


        engineersRepository.save(engineer1);
        engineersRepository.save(engineer2);

        squadsRepository.save(bmpSquad);
        squadsRepository.save(bigDataSquad);
        squadsRepository.save(qaEnablementSquad);
        squadsRepository.save(contextualSquad);


        // Passed Report
        {
            final String passedReportFilename;
            final Path passedReportPath;

            passedReportFilename = "bmp_passed_report";
            passedReportPath = Path.of(ROOT_PATH_DIRECTORY, bmpSquad.getSquadName(), passedReportFilename);

            passedReport = Reports.builder()
                    .uploadedBy(engineer1.getEngineerId())
                    .squadId(bmpSquad.getSquadId())
                    .filename(passedReportFilename)
                    .folderPath(passedReportPath.toString())
                    .reportStatus(ReportStatus.PASSED)
                    .build();
        }

        // Incomplete Report
        {
            final String incompleteReportFilename;
            final Path incompleteReportPath;

            incompleteReportFilename = "contextual_incomplete_report";
            incompleteReportPath = Path.of(ROOT_PATH_DIRECTORY, contextualSquad.getSquadName(), incompleteReportFilename);

            incompleteReport = Reports.builder()
                    .uploadedBy(engineer2.getEngineerId())
                    .squadId(contextualSquad.getSquadId())
                    .filename(incompleteReportFilename)
                    .folderPath(incompleteReportPath.toString())
                    .reportStatus(ReportStatus.INCOMPLETE)
                    .build();
        }

        // Failed Report
        {
            final String failedReportFilename;
            final Path failedReportPath;

            failedReportFilename = "enablement_failed_report";
            failedReportPath = Path.of(ROOT_PATH_DIRECTORY, qaEnablementSquad.getSquadName(), failedReportFilename);

            failedReport = Reports.builder()
                        .uploadedBy(engineer1.getEngineerId())
                        .squadId(qaEnablementSquad.getSquadId())
                        .filename(failedReportFilename)
                        .folderPath(failedReportPath.toString())
                        .reportStatus(ReportStatus.FAILED)
                        .build();
        }

        // Broken Report
        {
            final String brokenReportFilename;
            final Path brokenReportPath;

            brokenReportFilename = "big_data_broken_report";
            brokenReportPath = Path.of(ROOT_PATH_DIRECTORY, bigDataSquad.getSquadName(), brokenReportFilename);

            brokenReport = Reports.builder()
                        .uploadedBy(engineer2.getEngineerId())
                        .squadId(bigDataSquad.getSquadId())
                        .filename(brokenReportFilename)
                        .folderPath(brokenReportPath.toString())
                        .reportStatus(ReportStatus.BROKEN)
                        .build();
        }
    }

    @Test
    void ReportsRepository_SavedReports_SaveReports() {

        // Arrange
        final Reports report1;

        // Act
        report1 = reportsRepository.save(passedReport);

        // Assert
        assertNotNull(report1);
    }

    @Test
    void ReportsRepository_findAll_ReturnAllReports() {

        // Arrange
        final Reports passedReport1;
        final Reports incompleteReport1;
        final Reports  failedReport1;
        final List<Reports> allReports;

        passedReport1 = reportsRepository.save(passedReport);
        incompleteReport1 = reportsRepository.save(incompleteReport);
        failedReport1 = reportsRepository.save(failedReport);

        // Act
        allReports = reportsRepository.findAll();

        // Assert
        assertNotNull(allReports);

        assertEquals(3, allReports.size());
        assertEquals(passedReport1, allReports.getFirst());
        assertEquals(incompleteReport1, allReports.get(1));
        assertEquals(failedReport1, allReports.getLast());
    }

    @Test
    void ReportsRepository_findById_ReturnReportsByIdSearched() {

        // Arrange
        final Reports brokenReport1;
        final Reports passedReport1;

        final Reports searchedReport1;
        final Reports searchedReport2;

        brokenReport1  = reportsRepository.save(brokenReport);
        passedReport1 = reportsRepository.save(passedReport);

        // Act
        searchedReport1 = reportsRepository.findById(brokenReport1.getReportId()).get();
        searchedReport2 = reportsRepository.findById(passedReport1.getReportId()).get();

        // Assert

        assertNotNull(searchedReport1);

        assertEquals(brokenReport1, searchedReport1);
        assertEquals(passedReport1, searchedReport2);

        assertEquals(brokenReport1.getFilename(), searchedReport1.getFilename());
        assertEquals(passedReport1.getFilename(), searchedReport2.getFilename());

        assertEquals(brokenReport1.getReportStatus(), searchedReport1.getReportStatus());
        assertEquals(passedReport1.getReportStatus(), searchedReport2.getReportStatus());

        assertEquals(brokenReport1.getSquadId(), searchedReport1.getSquadId());
        assertEquals(passedReport1.getSquadId(), searchedReport2.getSquadId());
    }

    @Test
    void ReportsRepository_findByFilename_ReturnReportsByFilenameSearched() {

        // Arrange
        final Reports failedReport1;
        final Reports incompleteReport1;

        final Reports searchedReports1;
        final Reports searchedReports2;

        failedReport1 = reportsRepository.save(failedReport);
        incompleteReport1 = reportsRepository.save(incompleteReport);

        // Act
        searchedReports1 = reportsRepository.findByFilename(failedReport1.getFilename()).get();
        searchedReports2 = reportsRepository.findByFilename(incompleteReport1.getFilename()).get();

        // Assert
        assertNotNull(searchedReports1);

        assertEquals(failedReport1.getFilename(), searchedReports1.getFilename());
        assertEquals(incompleteReport1.getFilename(), searchedReports2.getFilename());

        assertEquals(failedReport1, searchedReports1);
        assertEquals(incompleteReport1, searchedReports2);
    }

    @Test
    void ReportsRepository_findByReportStatus_ReturnReportsByReportStatusSearched() {

        // Arrange
        final Reports passedReport1;
        final Reports failedReport1;

        final List<Reports> foundPassedReports;
        final List<Reports> foundFailedReports;
        final List<Reports> foundIncompleteReports;

        passedReport1 = reportsRepository.save(passedReport);
        failedReport1 = reportsRepository.save(failedReport);

        // Act
        foundPassedReports = reportsRepository.findByReportStatus(ReportStatus.PASSED);
        foundFailedReports = reportsRepository.findByReportStatus(ReportStatus.FAILED);
        foundIncompleteReports = reportsRepository.findByReportStatus(ReportStatus.INCOMPLETE);

        // Assert
        assertEquals(0, foundIncompleteReports.size());
        assertEquals(1, foundPassedReports.size());
        assertEquals(1, foundFailedReports.size());

        assertEquals(passedReport1, foundPassedReports.getFirst());
        assertEquals(failedReport1, foundFailedReports.getFirst());
    }
    
    @Test
    void ReportsRepository_findByDateUploaded_ReturnsReportsByDateUploadedSearched() {
        
        // Arrange
        final Reports brokenReport1;
        final Reports incompleteReport1;
        final LocalDate dateToday;

        dateToday = LocalDate.now();

        final List<Reports> reportsCreatedToday;

        brokenReport1 = reportsRepository.save(brokenReport);
        incompleteReport1 = reportsRepository.save(incompleteReport);
        
        // Act
        reportsCreatedToday = reportsRepository.findByDateUploaded(dateToday);

        // Assert
        assertEquals(2, reportsCreatedToday.size());

        assertEquals(brokenReport1, reportsCreatedToday.getFirst());
        assertEquals(incompleteReport1, reportsCreatedToday.getLast());
    }

    @Test
    void ReportsRepository_deleteByFilename_DeletesReportsByFilename() {

        // Arrange
        final Reports failedReport1;

        failedReport1 = reportsRepository.save(failedReport);

        // Act
        reportsRepository.deleteByFilename(failedReport1.getFilename());

        // Assert
        assertThat(reportsRepository.findById(failedReport1.getReportId()).isEmpty())
                .isTrue();
    }
}
