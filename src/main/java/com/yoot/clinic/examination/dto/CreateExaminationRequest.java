package com.yoot.clinic.examination.dto;

import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateExaminationRequest(
        Long patientId,
        String referringPhysician,
        LocalDate examinationDate,
        String notes,
        BigDecimal discount,
        @NotEmpty List<Long> serviceDetails
) {
}
