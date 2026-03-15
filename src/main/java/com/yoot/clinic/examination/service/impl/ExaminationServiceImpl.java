package com.yoot.clinic.examination.service.impl;

import com.yoot.clinic.examination.dto.CreateExaminationRequest;
import com.yoot.clinic.examination.dto.ExaminationReponse;
import com.yoot.clinic.examination.entity.Examination;
import com.yoot.clinic.examination.repository.ExaminationRepository;
import com.yoot.clinic.examination.service.ExaminationService;
import com.yoot.clinic.patient.dto.PatientResponse;
import com.yoot.clinic.patient.entity.Patient;
import com.yoot.clinic.patient.repository.PatientRepository;
import com.yoot.clinic.patient.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository examinationRepository;
    private final PatientService patientService;

    @Override
    public ExaminationReponse createExamination(CreateExaminationRequest request) {
        Patient patient = patientService.findPatientById(request.patientId());

        if (request.discount() != null && request.discount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }

        // find services by IDs and calculate total cost
        // calculate total cost based on services and discount
        BigDecimal totalCost = BigDecimal.ZERO;

        Examination examination = new Examination();
        examination.setPatient(patient);
        examination.setExaminationDate(request.examinationDate());
        examination.setDiscount(request.discount());
        examination.setReferringPhysician(request.referringPhysician());
        examination.setNotes(request.notes());
//        examination.setServices(request.serviceDetails());

        Examination savedExamination = examinationRepository.save(examination);
        return new ExaminationReponse(
                savedExamination.getId(),
                savedExamination.getPatient().getId(),
                savedExamination.getReferringPhysician(),
                savedExamination.getExaminationDate(),
                savedExamination.getNotes(),
                savedExamination.getDiscount(),
                totalCost
        );
    }
}
