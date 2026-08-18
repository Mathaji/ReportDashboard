package com.reports.quality_assurance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

/**
 * Entity class mapping a database schema for a Reports model
 *
 * @author Karabo Phoshoko
 * @version 26.0.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reports", indexes = {@Index(name = "squad_report_idx",
                                           columnList = "squad_id, report_status")})
public class Reports
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Long uploadedBy;
    private Long squadId;
    @Column(name = "filename", nullable = false)
    private String filename;
    private String folderPath;

    @CreationTimestamp
    private LocalDate dateUploaded;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus;
}