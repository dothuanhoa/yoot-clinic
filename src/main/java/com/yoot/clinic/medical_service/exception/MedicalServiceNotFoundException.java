package com.yoot.clinic.medical_service.exception;

public class MedicalServiceNotFoundException extends RuntimeException {
    public MedicalServiceNotFoundException(String message) {
        super(message);
    }
}
