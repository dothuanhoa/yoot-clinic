package com.yoot.clinic.medical_service.entity;

import com.yoot.clinic.medical_service.MedicalEnum.CategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "medicalServices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalService {
    //id, code: str, name: str, price: bigDecimal, category: enum, and isActive: boolean
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CategoryEnum category;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean isActive = true;
}
