package com.yoot.clinic.medical_service.mapper;

import com.yoot.clinic.medical_service.dto.MedicalServiceResponse;
import com.yoot.clinic.medical_service.entity.MedicalService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicalServiceMapper {
   MedicalServiceResponse toResponse (MedicalService medicalService);
}
