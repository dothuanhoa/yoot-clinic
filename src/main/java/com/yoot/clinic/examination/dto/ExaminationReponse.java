package com.yoot.clinic.examination.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExaminationReponse(
        Long id,
        Long patientId,
        String referringPhysician,
        LocalDate examinationDate,
        String notes,
        BigDecimal discount,
        BigDecimal totalCost
) {
}
