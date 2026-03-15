package com.yoot.clinic.patient.service;

import com.yoot.clinic.common.response.PageResponse;
import com.yoot.clinic.patient.dto.CreatePatientRequest;
import com.yoot.clinic.patient.dto.PatientResponse;
import com.yoot.clinic.patient.dto.UpdatePatientRequest;
import com.yoot.clinic.patient.entity.Patient;

public interface PatientService {

    PatientResponse create(CreatePatientRequest request);

    PageResponse<PatientResponse> findAll(String search, int page, int size);

    PatientResponse findById(Long id);

    PatientResponse update(Long id, UpdatePatientRequest request);

    void delete(Long id);

    Patient findPatientById(Long id);
}
