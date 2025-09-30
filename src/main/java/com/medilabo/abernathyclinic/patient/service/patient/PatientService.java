package com.medilabo.abernathyclinic.patient.service.patient;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medilabo.abernathyclinic.patient.constants.ApiMessages;
import com.medilabo.abernathyclinic.patient.dto.AddressDto;
import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;
import com.medilabo.abernathyclinic.patient.dto.MinimalPatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.dto.UpdatePatientDto;
import com.medilabo.abernathyclinic.patient.entity.Address;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;
import com.medilabo.abernathyclinic.patient.service.address.AddressService;

import jakarta.transaction.Transactional;

@Service
public class PatientService {
	private final PatientRepository patientRepository;
	private final PatientMapper patientMapper;
	private final AddressService addressService;
	
	public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, 
			AddressService addressService) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.addressService = addressService;
	}
	
	public PatientDto findPatientById(Long id) throws PatientNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("No patient found with id " + id));
		
		return patientMapper.patientToPatientDto(patient);
	}

	public List<MinimalPatientDto> findAllPatient() {
		List<Patient> patientsList = patientRepository.findAll();
		
		List<MinimalPatientDto> dtoList = patientMapper.patientsListToMinimalPatientDtoList(patientsList);
		
		return dtoList;
	}

	public PatientDto createPatient(CreatePatientDto dto) {
		Patient newPatient = patientMapper.createPatientDtoToPatient(dto);

		if (dto.address() != null) {
			Address patientAddress = addAddressIfNotExists(dto.address());
			newPatient.setAddress(patientAddress);
		}

		return patientMapper.patientToPatientDto(patientRepository.save(newPatient));
	}
	
	public Address addAddressIfNotExists(AddressDto address) {
		return addressService.addAddress(address);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public PatientDto updatePatient(Long id, UpdatePatientDto dto) throws PatientNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + id));
		
		if (!patient.getLastName().equalsIgnoreCase(dto.lastName())) {
			patient.setLastName(dto.lastName());
		}
		
		if (!patient.getFirstName().equalsIgnoreCase(dto.firstName())) {
			patient.setFirstName(dto.firstName());
		}
		
		if (!patient.getGender().equalsIgnoreCase(dto.gender())) {
			patient.setGender(dto.gender());
		}
		
		if (dto.address() != null) {
			Address providedAddress = addAddressIfNotExists(dto.address());
			
			if (!patient.getAddress().equals(providedAddress)) {
				patient.setAddress(providedAddress);
			}
		}
		
		if (!patient.getPhone().equalsIgnoreCase(dto.phone())) {
			patient.setPhone(dto.phone());
		}

		return patientMapper.patientToPatientDto(patient);
	}
}
