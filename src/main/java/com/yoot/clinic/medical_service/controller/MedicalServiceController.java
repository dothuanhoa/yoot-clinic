package com.yoot.clinic.medical_service.controller;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;
import com.yoot.clinic.medical_service.entity.MedicalService;
import com.yoot.clinic.medical_service.exception.InvalidCodeException;
import com.yoot.clinic.medical_service.service.MedicalServiceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medical-services")
@RequiredArgsConstructor
public class MedicalServiceController {
    private final MedicalServiceUseCase medicalServiceUseCase;

    @GetMapping
    public List<MedicalService> getAll(){
        return medicalServiceUseCase.getAll();
    }

    @GetMapping(params = "category")
    public List<MedicalService> getAllByCategory(@RequestParam(name = "category") String category){
        return medicalServiceUseCase.getAllByCategory(CategoryEnum.valueOf(category));
    }

    @GetMapping(params = "code")
    public MedicalService getByCode(@RequestParam(name = "code") String code){
        if (code.length()>50)
            throw new InvalidCodeException("code must not exceed 50 characters");
        return medicalServiceUseCase.getByCode(code);
    }
}
