package com.yoot.clinic.medical_service.dto;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;

import java.math.BigDecimal;

public record MedicalServiceResponse(
        String code,
        String name,
        BigDecimal price,
        CategoryEnum category,
        boolean isActive
) {

}
