package com.medilabo.abernathyclinic.patient.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.medilabo.abernathyclinic.patient.dto.ErrorDto;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<ErrorDto> handlePatientNotFoundException(PatientNotFoundException e) {
		return ResponseEntity.status(404).body(new ErrorDto(404, e.getMessage()));
	}
}
