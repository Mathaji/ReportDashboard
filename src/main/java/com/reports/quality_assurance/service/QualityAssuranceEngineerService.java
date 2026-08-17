package com.reports.quality_assurance.service;

import com.reports.quality_assurance.controller.dto.RegisterQualityAssuranceEngineerDTO;
import com.reports.quality_assurance.controller.dto.RegisteredQualityAssuranceEngineerDTO;
import com.reports.quality_assurance.model.QualityAssuranceEngineers;
import com.reports.quality_assurance.model.repository.QualityAssuranceEngineersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Service class handling business logic for Quality Assurance engineers.
 *
 * @author Karabo Phoshoko
 * @version 26.0.0.1
 */

@Service
@RequiredArgsConstructor
public class QualityAssuranceEngineerService {

    /** Repository for managing <b>QA Engineer</b> database operations */
    private final QualityAssuranceEngineersRepository engineersRepository;

    /**
     * Registers a new Quality Assurance engineer in the system.
     *
     * @param newEngineer the DTO containing the engineer's registration details
     * @return the saved DTO representing the <b>registered engineer</b>
     * @throws IllegalArgumentException if the username provided is already in use
     */
    @Transactional
    public RegisteredQualityAssuranceEngineerDTO registerNewEngineer(final RegisterQualityAssuranceEngineerDTO newEngineer) {
        final QualityAssuranceEngineers engineer;
        final QualityAssuranceEngineers saveEngineer;

        engineer = new QualityAssuranceEngineers();


        if (engineersRepository.findByUsername(newEngineer.getUsername()).isPresent()){
            throw new IllegalArgumentException("Username already in use");
        }

        //
        engineer.setFirstName(newEngineer.getFirstName());
        engineer.setLastName(newEngineer.getLastName());
        engineer.setUsername(newEngineer.getUsername());
        saveEngineer = engineersRepository.save(engineer);

        return new RegisteredQualityAssuranceEngineerDTO(saveEngineer.getEngineerId(),
                                               saveEngineer.getUsername(),
                                               saveEngineer.getAccountCreated()
        );
    }


    /**
     * Retrieves all Quality Assurance engineers from the system.
     *
     * @return a list containing all engineers, or an empty list if none exist
     */
    @Transactional(readOnly = true)
    public List<QualityAssuranceEngineers> getAllEngineers() {
        return engineersRepository.findAll();
    }

    /**
     * Retrieves a list of Quality Assurance Engineers based on a set range of when the account was created.
     *
     * @param startDate the start of the date range to search in
     * @param endDate the end of the date range to search in
     * @return a list containing Engineers within the <b>inputted time range</b>
     * @throws IllegalArgumentException if list is empty or null
     */
    @Transactional(readOnly = true)
    public List<QualityAssuranceEngineers> getEngineersInRange(final String startDate, final String endDate)
    {
        final DateTimeFormatter dateFormat;
        final LocalDate         formattedStartDate;
        final LocalDate         formattedEndDate;
        final List<QualityAssuranceEngineers> foundEngineers;

        dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        formattedStartDate = LocalDate.parse(startDate.strip(), dateFormat);
        formattedEndDate   = LocalDate.parse(endDate.strip(), dateFormat);

        foundEngineers = engineersRepository.findByAccountCreatedBetween(formattedStartDate.atStartOfDay(),
                                                                         formattedEndDate.atTime(LocalTime.MAX));

        if (foundEngineers == null || foundEngineers.isEmpty()) {
            throw new IllegalArgumentException(String.format("No Engineer accounts created between: %s and %s found", startDate, endDate));
        }

        return foundEngineers;
    }
}