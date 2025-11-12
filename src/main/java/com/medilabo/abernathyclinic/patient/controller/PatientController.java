package com.medilabo.abernathyclinic.patient.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;
import com.medilabo.abernathyclinic.patient.dto.MinimalPatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientReportInfoDto;
import com.medilabo.abernathyclinic.patient.dto.UpdatePatientDto;
import com.medilabo.abernathyclinic.patient.exception.IncompleteAddressException;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.service.patient.PatientService;

import jakarta.validation.Valid;

@RestController
public class PatientController {
    private final PatientService patientService;

    PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/api/patient/id/{id}")
	public PatientDto getPatientById(@PathVariable Long id) throws PatientNotFoundException {	
		return patientService.findPatientById(id);
	}
	
	@GetMapping("/api/patient/uuid/{uuid}")
	public PatientDto getPatientByUuid(@PathVariable UUID uuid) throws PatientNotFoundException {
		// si la valeur du pathvariable n'est pas un UUID valide --> erreur 400
		return patientService.findPatientByUuid(uuid);
	}
	
	@GetMapping("/api/patients")
	public List<MinimalPatientDto> getAllPatients() {
		return patientService.findAllPatient();
	}
	
	@PostMapping("/api/patient")
	public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody CreatePatientDto dto) throws IncompleteAddressException {
		return ResponseEntity.status(201).body(patientService.createPatient(dto));
	}
	
	@PatchMapping("/api/patient/{id}")
	public PatientDto updatePatient(@PathVariable Long id, @Valid @RequestBody UpdatePatientDto dto) throws PatientNotFoundException, IncompleteAddressException {
		return patientService.updatePatient(id, dto);
	}
	
	@PatchMapping("/api/patient/{uuid}/update")
	public PatientDto updatePatientByUuid(@PathVariable UUID uuid, @Valid @RequestBody UpdatePatientDto dto) throws PatientNotFoundException, IncompleteAddressException {
		return patientService.updatePatientByUuid(uuid, dto);
	}
	
	@GetMapping("/api/patient/{uuid}/report-info")
	public PatientReportInfoDto getPatientReportInfo(@PathVariable UUID uuid) {
		return patientService.getPatientInfoForReport(uuid);
	}
}
