package com.medilabo.abernathyclinic.patient.dto;

import java.time.LocalDate;

public record PatientReportInfoDto(LocalDate birthDate, String gender) {}
