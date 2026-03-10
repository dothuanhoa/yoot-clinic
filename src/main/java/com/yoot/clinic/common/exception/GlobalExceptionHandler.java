package com.yoot.clinic.common.exception;

import com.yoot.clinic.medical_service.exception.InvalidCodeException;
import com.yoot.clinic.medical_service.exception.MedicalServiceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(422))
                .body(ErrorResponse.ofValidation(
                        422,
                        "Validation Failed",
                        "One or more fields failed validation",
                        fieldErrors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getConstraintViolations()
                .forEach(cv -> fieldErrors.put(cv.getPropertyPath().toString(), cv.getMessage()));
        return ResponseEntity.status(HttpStatusCode.valueOf(422))
                .body(ErrorResponse.ofValidation(
                        422,
                        "Validation Failed",
                        "One or more parameters failed validation",
                        fieldErrors));
    }

    @ExceptionHandler(MedicalServiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMedicalServiceException(MedicalServiceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.ofValidation(
                        404,
                        "ERR-MED-001",
                        "No Resouce found with that code",
                        null
                ));
    }

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCode(InvalidCodeException ex){
        return ResponseEntity.status(400)
                .body(ErrorResponse.ofValidation(
                        400,
                        "ERR-REQ-001",
                        ex.getMessage(),
                        null
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("[Unhandled exception]", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(
                        500,
                        "Internal Server Error",
                        "An unexpected error occurred"));
    }

}
