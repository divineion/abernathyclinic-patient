package com.medilabo.abernathyclinic.patient.dto;

import java.time.LocalDate;

public record CreatePatientDto(String firstName, String lastName, LocalDate birthDate, String gender, AddressDto address, String phone) {

}
