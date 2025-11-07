package com.medilabo.abernathyclinic.patient.dto;

import jakarta.validation.constraints.Size;

public record AddressDto(
		@Size(min = 0, max=5) String streetNumber, 
		@Size(min = 0, max=50) String street, 
		@Size(min = 0, max=50) String city,
		@Size(min = 0, max=5) String zip) {}
