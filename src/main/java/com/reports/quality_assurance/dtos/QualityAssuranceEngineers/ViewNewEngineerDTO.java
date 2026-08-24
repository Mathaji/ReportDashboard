package com.reports.quality_assurance.dtos.QualityAssuranceEngineers;

import java.time.LocalDate;

/**
 * DTO class handling outgoing requests for registered Quality Assurance Engineer.
 *
 * @param fullEngineerName the full name of the registered Quality Assurance Engineer
 * @param username the unique username <i>(Corresponding to the registered username on the QA System)</i>
 * @param timeEngineerCreated the time the Quality Assurance Engineer's account was created
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */
public record ViewNewEngineerDTO(
        String    fullEngineerName,
        String    username,
        LocalDate timeEngineerCreated
) {
}
