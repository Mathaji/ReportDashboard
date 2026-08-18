package com.reports.quality_assurance.model;

/**
 * A class that represents the ReportStatus enum class.
 * The class allows for 4 different type of statuses:
 * <ul>
 * <li>PASSED</li>
 * <li>FAILED</li>
 * <li>BROKEN</li>
 * <li>INCOMPLETE</li>
 * </ul>
 *
 * @author Karabo Phoshoko
 * version: 26.0.0
 */

public enum ReportStatus

{
    /** The test execution was successful. */
    PASSED,

    /** The test execution failed due to an assertion error. */
    FAILED,

    /** The test execution was interrupted by an unexpected exception. */
    BROKEN,

    /** The test execution did not finish. */
    INCOMPLETE
}
