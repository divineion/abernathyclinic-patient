package com.medilabo.abernathyclinic.patient.dto;

import jakarta.validation.constraints.Size;

public record AddressDto(
		@Size(min = 1, max=5) String streetNumber, 
		@Size(min = 2, max=50) String street, 
		@Size(min = 2, max=50) String city,
		@Size(min = 2, max=5) String zip) {}
