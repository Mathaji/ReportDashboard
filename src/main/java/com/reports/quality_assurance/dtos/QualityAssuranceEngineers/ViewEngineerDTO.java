package com.reports.quality_assurance.dtos.QualityAssuranceEngineers;

import com.reports.quality_assurance.model.Squads;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO class handling outgoing requests for registered Quality Assurance Engineer.
 *
 * @param fullEngineerName the full name of the registered Quality Assurance Engineer
 * @param username the unique username <i>(Corresponding to the registered username on the QA System)</i>
 * @param userSquads a list of the Squads the Quality Assurance Engineer has uploaded reports for
 * @param timeEngineerCreated the time the Quality Assurance Engineer's account was created
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */
public record ViewEngineerDTO(
        String      fullEngineerName,
        String      username,
        Set<Squads> userSquads,
        LocalDate   timeEngineerCreated
)
{}
