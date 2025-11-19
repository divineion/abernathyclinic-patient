package com.medilabo.abernathyclinic.patient.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UpdatePatientDto(
		@NotBlank @Size(min=2, max=80) String firstName, 
		@NotBlank @Size(min=2, max=80) String lastName, 
		@Valid AddressDto address,
		@Size(min=0, max=20) String phone) {
	public UpdatePatientDto(String firstName, String lastName, String gender) {
		this(firstName, lastName, null, null);
	}
}
