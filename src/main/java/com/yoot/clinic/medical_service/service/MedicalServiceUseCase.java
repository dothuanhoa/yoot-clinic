package com.yoot.clinic.medical_service.service;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;
import com.yoot.clinic.medical_service.entity.MedicalService;

import java.util.List;

public interface MedicalServiceUseCase {
    MedicalService getByCode(String code);
    List<MedicalService> getAllByCategory(CategoryEnum category);
    List<MedicalService> getAll();

}
