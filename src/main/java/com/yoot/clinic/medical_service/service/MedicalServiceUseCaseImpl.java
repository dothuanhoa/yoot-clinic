package com.yoot.clinic.medical_service.service;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;
import com.yoot.clinic.medical_service.entity.MedicalService;
import com.yoot.clinic.medical_service.exception.MedicalServiceNotFoundException;
import com.yoot.clinic.medical_service.mapper.MedicalServiceMapper;
import com.yoot.clinic.medical_service.repository.MedicalServiceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalServiceUseCaseImpl implements MedicalServiceUseCase{
    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalServiceMapper medicalServiceMapper;

    public MedicalServiceUseCaseImpl(MedicalServiceRepository medicalServiceRepository, MedicalServiceMapper medicalServiceMapper) {
        this.medicalServiceRepository = medicalServiceRepository;
        this.medicalServiceMapper = medicalServiceMapper;
    }

    @Override
    public MedicalService getByCode(String code) {
        return medicalServiceRepository.findByCode(code)
                .orElseThrow(()->new MedicalServiceNotFoundException("Cant find that code"));
    }

    @Override
    public List<MedicalService> getAllByCategory(CategoryEnum category) {

        return medicalServiceRepository.findAllByCategoryOrderByCodeAsc(category);
    }

    @Override
    public List<MedicalService> getAll() {
        return medicalServiceRepository.findAll(Sort.by(Sort.Direction.ASC, "code"));
    }

}
