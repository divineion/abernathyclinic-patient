package com.medilabo.abernathyclinic.patient.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PatientDto(UUID uuid, String lastName, String firstName, LocalDate birthDate, String gender, AddressDto address) {}
