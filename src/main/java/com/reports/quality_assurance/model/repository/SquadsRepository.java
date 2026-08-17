package com.reports.quality_assurance.model.repository;

import com.reports.quality_assurance.model.Squads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing interactions with {@link Squads}
 *
 * @author Karabo Phoshoko
 * @version 26.0.0.1
 */

@Repository
public interface SquadsRepository extends JpaRepository<Squads, Long> {

    /**
     * Searches the squad database for a specific squad name
     *
     * @param squadName the name of the exact squad to validate
     * @return an Optional containing the found squad or empty if none were found
     */
    Optional<Squads> findBySquadName(final String squadName);
}
