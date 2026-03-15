package com.yoot.clinic.examination.entity;

import com.yoot.clinic.medical_service.entity.MedicalService;
import com.yoot.clinic.patient.entity.Patient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "examinations")
@Getter
@Setter
public class Examination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(name = "referring_physician", nullable = true, length = 255)
    private String referringPhysician;

    @Column(name = "examination_date", nullable = false)
    private LocalDate examinationDate;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "discount")
    private BigDecimal discount;

    @OneToMany(mappedBy = "examination")
    private List<MedicalService> services;
}
