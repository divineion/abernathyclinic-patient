package com.medilabo.abernathyclinic.patient.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


public record CreatePatientDto(
		@NotBlank @Size(min=2, max=80) String firstName, 
		@NotBlank @Size(min=2, max=80) String lastName, 
		@NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate, 
		@NotBlank @Size(min=1, max=10) String gender, 
		AddressDto address, 
		String phone) {
	public CreatePatientDto(String firstName, String lastName, LocalDate birthDate, String gender) {
		this(firstName, lastName, birthDate, gender, null, null);
	}
}
