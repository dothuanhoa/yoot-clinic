package com.yoot.clinic.examination.controller;

import com.yoot.clinic.examination.dto.CreateExaminationRequest;
import com.yoot.clinic.examination.dto.ExaminationReponse;
import com.yoot.clinic.examination.service.ExaminationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examinations")
@Validated
@AllArgsConstructor
public class ExaminationController {

    private final ExaminationService examinationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExaminationReponse createExamination(CreateExaminationRequest createExaminationRequest) {
        return examinationService.createExamination(createExaminationRequest);
    }
}
