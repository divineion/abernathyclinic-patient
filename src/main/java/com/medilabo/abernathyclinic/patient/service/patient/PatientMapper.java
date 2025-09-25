package com.medilabo.abernathyclinic.patient.service.patient;

import org.mapstruct.Mapper;

import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.service.address.AddressMapper;

@Mapper(componentModel="spring", uses=AddressMapper.class)
public interface PatientMapper {
	
	public PatientDto patientToPatientDto(Patient patient);
}
