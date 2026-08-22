package com.reports.quality_assurance.repository;

import com.reports.quality_assurance.model.QualityAssuranceEngineers;
import com.reports.quality_assurance.model.repository.QualityAssuranceEngineersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Test suite for {@link QualityAssuranceEngineersRepository} to validate standard <b>CRUD operations</b> and <b>Custom Query boundaries</b>:
 * <ul>
 *     <li>{@code save()} method to save new Quality Assurance Engineers to the database</li>
 *     <li>{@code findAll()} method to find all saved Quality Assurance Engineers in the database</li>
 *     <li>{@code findById(Long id)} method to find Quality Assurance Engineers in the database by <b>their ID</b></li>
 *     <li>{@code findByUsername(String username} method to find Quality Assurance Engineers in the database by <b>their username</b></li>
 *     <li>{@code findByAccountCreatedBetween(LocalDateTime startDate, LocalDateTime endDate} method to find Quality Assurance Engineers in the database by <b>the date their accounts were created</b></li>
 * </ul>
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class QualityAssuranceEngineersRepositoryTest {

    @Autowired
    private QualityAssuranceEngineersRepository engineersRepository;

    private QualityAssuranceEngineers engineer_1;
    private QualityAssuranceEngineers engineer_2;


    @BeforeEach
    void setUp() {

        // Arrange
        engineer_1 = QualityAssuranceEngineers.builder()
                .firstName("Karabo")
                .lastName("Phoshoko")
                .username("phosk002")
                .build();

        engineer_2 = QualityAssuranceEngineers.builder()
                .firstName("Nehal")
                .lastName("Mohanlal")
                .username("mohan002")
                .build();
    }

    @Test
    void QualityAssuranceEngineersRepository_SaveEngineer_ReturnSavedEngineer() {
        //Arrange
        final QualityAssuranceEngineers savedEngineer_1;
        final QualityAssuranceEngineers savedEngineer_2;

        //Act
        savedEngineer_1 = engineersRepository.save(engineer_1);
        savedEngineer_2 = engineersRepository.save(engineer_2);

        //Assert
        Assertions.assertNotNull(savedEngineer_1);
        Assertions.assertNotNull(savedEngineer_2);

        // Assert Database auto-increment user ID on creation
        Assertions.assertEquals(engineer_1.getEngineerId(), savedEngineer_1.getEngineerId());
        Assertions.assertEquals(engineer_2.getEngineerId(), savedEngineer_2.getEngineerId());

        // Assert actual Engineer credentials added in database
        Assertions.assertEquals(engineer_1.getFirstName(), savedEngineer_1.getFirstName());
        Assertions.assertEquals(engineer_1.getLastName(), savedEngineer_1.getLastName());
        Assertions.assertEquals(engineer_1.getUsername(), savedEngineer_1.getUsername());
        Assertions.assertEquals(engineer_1.getSquads(), savedEngineer_1.getSquads());

        Assertions.assertEquals(engineer_2.getFirstName(), savedEngineer_2.getFirstName());
        Assertions.assertEquals(engineer_2.getLastName(), savedEngineer_2.getLastName());
        Assertions.assertEquals(engineer_2.getSquads(), savedEngineer_2.getSquads());
        Assertions.assertEquals(engineer_2.getUsername(), savedEngineer_2.getUsername());
    }

    @Test
    void QualityAssuranceEngineersRepository_FindAll_ReturnsAllEngineers() {

        // Arrange
        final List<QualityAssuranceEngineers> allEngineersList;

        engineersRepository.save(engineer_1);
        engineersRepository.save(engineer_2);

        // Act
        allEngineersList = engineersRepository.findAll();

        // Assert
        Assertions.assertNotNull(allEngineersList);

        // Assert number of engineers found
        Assertions.assertEquals(2, allEngineersList.size());
    }

    @Test
    void QualityAssuranceEngineersRepository_FindById_ReturnsEngineer() {

        // Arrange
        final QualityAssuranceEngineers returnedEngineer_1;
        final QualityAssuranceEngineers returnedEngineer_2;

        engineersRepository.save(engineer_1);
        engineersRepository.save(engineer_2);

        // Act
        returnedEngineer_1 = engineersRepository.findById(engineer_1.getEngineerId()).get();
        returnedEngineer_2 = engineersRepository.findById(engineer_2.getEngineerId()).get();

        // Assert
        Assertions.assertNotNull(returnedEngineer_1);
        Assertions.assertNotNull(returnedEngineer_2);

        Assertions.assertEquals(engineer_1, returnedEngineer_1);
        Assertions.assertEquals(engineer_2, returnedEngineer_2);
    }

    @Test
    void QualityAssuranceEngineersRepository_FindByUsername_ReturnsEngineer() {

        // Arrange
        final QualityAssuranceEngineers returnedEngineer_1;
        final QualityAssuranceEngineers returnedEngineer_2;

        engineersRepository.save(engineer_1);
        engineersRepository.save(engineer_2);

        // Act
        returnedEngineer_1 = engineersRepository.findByUsername(engineer_1.getUsername()).get();
        returnedEngineer_2 = engineersRepository.findByUsername(engineer_2.getUsername()).get();

        // Assert
        Assertions.assertNotNull(returnedEngineer_1);
        Assertions.assertNotNull(returnedEngineer_2);

        Assertions.assertEquals(engineer_1, returnedEngineer_1);
        Assertions.assertEquals(engineer_2, returnedEngineer_2);
    }

    @Test
    void QualityAssuranceEngineersRepository_findByAccountCreatedBetween_ReturnsEngineers() {

        // Arrange
        final List<QualityAssuranceEngineers> engineersCreatedInRange;
        final LocalDateTime startRangeDate;
        final LocalDateTime endRangeDate;

        startRangeDate = LocalDateTime.now()
                        .minusDays(5)
                        .toLocalDate().atStartOfDay();
        endRangeDate = LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);

        engineersRepository.save(engineer_1);
        engineersRepository.save(engineer_2);

        // Act
        engineersCreatedInRange = engineersRepository.findByAccountCreatedBetween(startRangeDate, endRangeDate);

        // Assert
        Assertions.assertNotNull(engineersCreatedInRange);

        Assertions.assertEquals(2, engineersCreatedInRange.size());
        Assertions.assertEquals(engineer_1, engineersCreatedInRange.getFirst());
        Assertions.assertEquals(engineer_2, engineersCreatedInRange.getLast());

    }
}

