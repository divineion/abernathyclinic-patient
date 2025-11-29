package com.medilabo.abernathyclinic.patient.service.patient;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;
import com.medilabo.abernathyclinic.patient.dto.MinimalPatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.service.address.AddressMapper;

@Mapper(componentModel="spring", uses=AddressMapper.class)
public interface PatientMapper {
	
	public PatientDto patientToPatientDto(Patient patient);

	public List<MinimalPatientDto> patientsListToMinimalPatientDtoList(List<Patient> patientsList);
	
	@Mapping(target = "address", ignore = true)
    Patient createPatientDtoToPatient(CreatePatientDto dto);

}
