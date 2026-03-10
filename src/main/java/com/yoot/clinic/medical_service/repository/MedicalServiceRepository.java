package com.yoot.clinic.medical_service.repository;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;
import com.yoot.clinic.medical_service.entity.MedicalService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {

    Optional<MedicalService> findByCode(String code);

    List<MedicalService> findAllByCategoryOrderByCodeAsc(CategoryEnum category);
}
