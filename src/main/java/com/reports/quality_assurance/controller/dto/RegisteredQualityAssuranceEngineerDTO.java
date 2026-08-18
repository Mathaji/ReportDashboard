package com.reports.quality_assurance.controller.dto;

import java.time.LocalDateTime;

/**
 * DTO class handling outgoing requests for registered Quality Assurance Engineer.
 *
 * @param id unique ID identifier for the successfully created Quality Assurance Engineer
 * @param username the unique username <i>(Corresponding to the registered username on the QA System)</i>
 * @param timeAccountCreated the time the Quality Assurance Engineer's account was created
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

public record RegisteredQualityAssuranceEngineerDTO (
        Long id,
        String username,
        LocalDateTime timeAccountCreated )
{}
