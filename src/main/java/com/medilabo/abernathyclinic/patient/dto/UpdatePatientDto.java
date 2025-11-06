package com.medilabo.abernathyclinic.patient.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UpdatePatientDto(
		@NotBlank @Size(min=2, max=80) String firstName, 
		@NotBlank @Size(min=2, max=80) String lastName, 
		@NotBlank @Size(min=1, max=10) String gender, 
		@Valid AddressDto address, 
		String phone) {
	public UpdatePatientDto(String firstName, String lastName, String gender) {
		this(firstName, lastName, gender, null, null);
	}
}
