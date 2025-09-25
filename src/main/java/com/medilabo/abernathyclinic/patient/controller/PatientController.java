package com.medilabo.abernathyclinic.patient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.service.patient.PatientService;

@RestController
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    
	@GetMapping("/api/patient/{id}")
	public ResponseEntity<PatientDto> findById(@PathVariable Long id) throws PatientNotFoundException {	
		PatientDto patient = patientService.findById(id);
		return ResponseEntity.ok(patient);
	}
}
