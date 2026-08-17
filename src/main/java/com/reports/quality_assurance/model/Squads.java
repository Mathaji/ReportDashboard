package com.reports.quality_assurance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class mapping the database schema for a Squads model
 *
 * @author Karabo Phoshoko
 * @version 26.0.0.1
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "squads")
public class Squads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long squadId;

    @Column(name = "squad_name", unique = true, nullable = false)
    private String squadName;
}
