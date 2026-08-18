package com.reports.quality_assurance.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity class mapping the database schema for Quality Assurance engineers.
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quality_assurance_engineers")
public class QualityAssuranceEngineers
{

    /** The maximum character length allowed for an engineer's <b>name</b> and <b>surname</b>*/
    private static final int MAXIMUM_NAME_LENGTH       = 100;

    /** The maximum character length allowed for an engineer's <b>username</b> */
    private static  final int MAXIMUM_USERNAME_LENGTH  = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   engineerId;

    @Column(name = "first_name", length= MAXIMUM_NAME_LENGTH, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = MAXIMUM_NAME_LENGTH, nullable = false)
    private String lastName;

    @Column(name = "username", length = MAXIMUM_USERNAME_LENGTH, nullable = false, unique = true)
    private String username;

    @CreationTimestamp
    @Column(name = "account_created", nullable = false)
    private LocalDateTime accountCreated;

    @ManyToMany
    @JoinTable(
            name = "engineer_squads",
            joinColumns = @JoinColumn(name = "engineer_id"),
            inverseJoinColumns = @JoinColumn(name = "squad_id")
    )
    private Set<Squads> squads;
}
