package com.reports.quality_assurance.dtos.Reports;

import com.reports.quality_assurance.model.ReportStatus;
import lombok.Getter;

import java.nio.file.Path;

/**
 * DTO class handling incoming details for new reports.
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Getter
public class AddReportsDTO
{
    private final String       user;
    private final String       squad;
    private final String       filename;
    private       String       folderPath;
    private final ReportStatus reportStatus;

    private static final int    MINIMUM_FILENAME_LENGTH    = 4;
    private static final int    MAXIMUM_FILENAME_LENGTH    = 50;
    private static final int    MINIMUM_SQUAD_NAME_LENGTH  = 2;
    private static final int    MAXIMUM_SQUAD_NAME_LENGTH  = 30;

    /** The root directory path targeting the Reports folder.*/
    private static final String ROOT_PATH_DIRECTORY = "C:\\User\\Reports";

    /** The filename of the index HTML file used to resolve Allure report entries. */
    private static final String INDEX_HTML_PATH = "index.html";


    public AddReportsDTO(final String user,
                         final String squad,
                         final String filename,
                         final ReportStatus reportStatus) {

        this.user = setUser(user);
        this.squad = setSquad(squad);
        this.filename = setFilename(filename);
        this.folderPath = setFolderPath();
        this.reportStatus = setReportStatus(reportStatus);
    }

    /**
     * Sets a User variable.
     *
     * @param user the Quality Assurance Engineer's <b>username</b>
     * @return the accepted <b>user</b> variable
     * @throws IllegalArgumentException if user is null
     */
    private String setUser(final String user)
    {
        if(user == null)
        {
            throw new IllegalArgumentException("Engineer cannot be null!");
        }

        return user;
    }

    /**
     * Sets a <b>Squad</b> variable.
     *
     * @param squad the <b>Squad</b> that the report belongs to
     * @return the accepted <b>squad</b> variable
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li>if squad is null</li>
     *                                  <li>if squad name length is less than {@linkplain #MINIMUM_SQUAD_NAME_LENGTH}</li>
     *                                  <li>if squad name is greater than {@linkplain #MAXIMUM_SQUAD_NAME_LENGTH}</li>
     *                                  </ul>
     */
    private String setSquad(final String squad) {
        if(squad == null) {
            throw new IllegalArgumentException("Squad cannot be null!");
        }
        if(squad.length() < MINIMUM_SQUAD_NAME_LENGTH || squad.length() > MAXIMUM_SQUAD_NAME_LENGTH) {
            throw new IllegalArgumentException(String.format("Squad name cannot be less than %d or greater than %d!", MINIMUM_SQUAD_NAME_LENGTH, MAXIMUM_SQUAD_NAME_LENGTH));
        }

        return squad;
    }

    /**
     * Sets the <b>Filename</b> variable.
     *
     * @param filename the <b>filename</b> that the report being generated will be saved under
     * @return the accepted <b>filename</b> variable
     * @throws IllegalArgumentException if any of the following validation conditions fail:
     *                                  <ul>
     *                                  <li> if filename is null</li>
     *                                  <li>if filename is less than {@linkplain #MINIMUM_FILENAME_LENGTH}</li>
     *                                  <li>if filename is greater than {@linkplain #MAXIMUM_FILENAME_LENGTH}</li>
     *                                  </ul>
     */
    private String setFilename(final String filename) {
        if(filename == null) {
            throw new IllegalArgumentException("Filename cannot be null!");
        }

        if(filename.length() < MINIMUM_FILENAME_LENGTH || filename.length() > MAXIMUM_FILENAME_LENGTH) {
            throw new IllegalArgumentException(String.format("Filename cannot be less than %d or greater than %d!", MINIMUM_FILENAME_LENGTH, MAXIMUM_FILENAME_LENGTH));
        }
        return filename;
    }

    /**
     * Sets the <b>Folder Path</b> variable.
     *
     * <p>
     * This method creates the absolute file path,
     * pointing to a specific reports {@linkplain #INDEX_HTML_PATH} using the system dependent path separator
     * </p>
     *
     * @return the <b>Folder Path</b> pointing to a specific reports <i>index.html</i>
     */
    private String setFolderPath() {
        final String path;
        path = Path.of(ROOT_PATH_DIRECTORY, getSquad(), getFilename(), INDEX_HTML_PATH).toString();

        folderPath = path;
        return folderPath;
    }

    /**
     * Sets the <b>Report Status</b> variable.
     *
     * @param reportStatus the <b>Status</b> of the report being uploaded, can either be:
     *                     <ul>
     *                     <li>PASSED</li>
     *                     <li>FAILED</li>
     *                     <li>BROKEN</li>
     *                     <li>INCOMPLETE</li>
     *                     </ul>
     * @return the status of the report
     * @throws IllegalArgumentException if the report status is null
     */
    private ReportStatus setReportStatus(final ReportStatus reportStatus) {
        if (reportStatus == null)
        {
            throw new IllegalArgumentException("Report Status cannot be null!");
        }

        return reportStatus;
    }
}
