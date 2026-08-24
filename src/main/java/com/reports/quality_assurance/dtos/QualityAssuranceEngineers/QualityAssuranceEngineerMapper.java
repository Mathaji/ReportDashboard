package com.reports.quality_assurance.dtos.QualityAssuranceEngineers;

import com.reports.quality_assurance.model.QualityAssuranceEngineers;
import com.reports.quality_assurance.model.Squads;

import java.time.LocalDate;
import java.util.Set;

/**
 * Mapper class handling data transformations for registered Quality Assurance Engineers.
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */
public class QualityAssuranceEngineerMapper {

    /**
     * Converts a Quality Assurance Engineer entity into a presentation-layer DTO.
     *
     * @param engineer the Quality Assurance Engineer entity containing original database records
     * @return a ViewEngineerDTO optimized for presentation layer outgoing requests
     */
    public static ViewEngineerDTO toDto(final QualityAssuranceEngineers engineer) {
        final ViewEngineerDTO viewEngineer;
        final String engineerFullName;
        final String engineerUsername;
        final Set<Squads> engineerSquads;
        final LocalDate    accountCreated;

        engineerFullName = engineer.getFirstName() + " " + engineer.getLastName();
        engineerUsername = engineer.getUsername();
        engineerSquads   = engineer.getSquads();
        accountCreated   = LocalDate.from(engineer.getAccountCreated());

        viewEngineer = new ViewEngineerDTO(
                        engineerFullName,
                        engineerUsername,
                        engineerSquads,
                        accountCreated);

        return viewEngineer;
    }
}
