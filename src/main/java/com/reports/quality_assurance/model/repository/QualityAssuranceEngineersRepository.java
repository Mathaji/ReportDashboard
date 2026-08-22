package com.reports.quality_assurance.model.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reports.quality_assurance.model.QualityAssuranceEngineers;

/**
 * Repository interface for managing interactions with {@link QualityAssuranceEngineers}
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Repository
public interface QualityAssuranceEngineersRepository extends JpaRepository<QualityAssuranceEngineers, Long>
{
    /**
     * Retrieves a Quality Assurance Engineer by their unique <b>Username</b> identifier
     *
     * @param username the system assigned <i>(Assigned by upon Vodacom account creation)</i> Quality Assurance Engineer's username
     * @return the unique <b>username</b>
     */
    Optional<QualityAssuranceEngineers> findByUsername(final String username);

    /**
     * Retrieves a list of Quality Assurance Engineer's by order of when the account was created
     *
     * @param start the start date which the account could be created
     * @param end the end date which the account could be created
     * @return a list of all the existing Quality Assurance Engineer within the selected date range
     */
    List<QualityAssuranceEngineers> findByAccountCreatedBetween(final LocalDateTime start, final LocalDateTime end);
}
