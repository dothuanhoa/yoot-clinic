package com.yoot.clinic.patient.service;

import com.yoot.clinic.common.exception.ResourceNotFoundException;
import com.yoot.clinic.common.response.PageResponse;
import com.yoot.clinic.patient.dto.CreatePatientRequest;
import com.yoot.clinic.patient.dto.PatientResponse;
import com.yoot.clinic.patient.dto.UpdatePatientRequest;
import com.yoot.clinic.patient.entity.Patient;
import com.yoot.clinic.patient.mapper.PatientMapper;
import com.yoot.clinic.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;
    private final PatientCodeGenerator patientCodeGenerator;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponse create(CreatePatientRequest request) {
        LocalDate visitDate = request.visitDate() != null
                ? request.visitDate()
                : LocalDate.now();

        int nextSeq = patientRepository.nextCodeSequence();
        String code = patientCodeGenerator.generate(visitDate, nextSeq);

        log.info("[Create] visitDate={} nextSeq={} code={}", visitDate, nextSeq, code);

        Patient patient = Patient.builder()
                .patientCode(code)
                .codeSequence(nextSeq)
                .fullName(request.fullName().strip())
                .birthYear(request.birthYear())
                .address(request.address())
                .phoneNumber(request.phoneNumber())
                .visitDate(visitDate)
                .build();

        return patientMapper.toResponse(patientRepository.save(patient));
    }

    @Override
    public PageResponse<PatientResponse> findAll(String search, int page, int size) {
        String term = (search != null && !search.isBlank()) ? search.strip() : null;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return PageResponse.from(
                patientRepository.findAllActive(term, pageable)
                        .map(patientMapper::toResponse));
    }

    @Override
    public PatientResponse findById(Long id) {
        return patientRepository.findByIdAndIsDeletedFalse(id)
                .map(patientMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }

    @Override
    @Transactional
    public PatientResponse update(Long id, UpdatePatientRequest request) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));

        patient.setFullName(request.fullName().strip());
        patient.setBirthYear(request.birthYear());
        patient.setAddress(request.address());
        patient.setPhoneNumber(request.phoneNumber());

        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
        patient.setDeleted(true);
        patient.setDeletedAt(LocalDateTime.now());
    }

    @Override
    public Patient findPatientById(Long id) {
        return patientRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", id));
    }
}
