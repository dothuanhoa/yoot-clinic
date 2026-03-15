package com.yoot.clinic.examination.service;

import com.yoot.clinic.examination.dto.CreateExaminationRequest;
import com.yoot.clinic.examination.dto.ExaminationReponse;


public interface ExaminationService {
    ExaminationReponse createExamination(CreateExaminationRequest examination);
}
