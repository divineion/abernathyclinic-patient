package com.medilabo.abernathyclinic.patient.service.patient;

import java.util.List;
import java.util.UUID;

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
	
	private boolean isEmptyAddress(AddressDto addressDto) {
		return addressDto.city().isBlank() 
				&& addressDto.street().isBlank() 
				&& addressDto.streetNumber().isBlank() 
				&& addressDto.zip().isBlank();
	}
	
	private boolean isPartialAddress(AddressDto dto) {
		boolean hasMissingFields = dto.city().isBlank() 
				|| dto.street().isBlank() 
				|| dto.streetNumber().isBlank() 
				|| dto.zip().isBlank();
		
		return !isEmptyAddress(dto) && hasMissingFields;
	}
	
	private boolean isSameAddress(AddressDto addressDto, Address address) {
		// on transfère la logiqe de protection contre les NPE ici
		if (address != null) {
			return addressDto.city().equalsIgnoreCase(address.getStreet().getCity().getName())
				&& addressDto.zip().equalsIgnoreCase(address.getStreet().getCity().getZip())
				&& addressDto.streetNumber().equalsIgnoreCase(address.getStreetNumber())
				&& addressDto.street().equalsIgnoreCase(address.getStreet().getName());		
		}
		
		return false;
	}
	
	public Address addAddressIfNotExists(AddressDto address) {
		return addressService.addAddress(address);
	}
	
	/**
	 * This method is used for communication between backend microservice. 
	 * @param long id
	 * @return
	 * @throws PatientNotFoundException
	 */
	public PatientDto findPatientById(Long id) throws PatientNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + id));
		
		return patientMapper.patientToPatientDto(patient);
	}
	
	/**
	 * This method is used by the client side application. 
	 * @param uuid
	 * @return
	 * @throws PatientNotFoundException
	 */
	public PatientDto findPatientByUuid(UUID uuid) throws PatientNotFoundException {
		Patient patient = patientRepository.findByUuid(uuid)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + uuid));
		
		return patientMapper.patientToPatientDto(patient);
	}

	/**
	 * Retrieves a list of all patients, returned as MinimalPatientDto (for list views).
	 * @return
	 */
	public List<MinimalPatientDto> findAllPatient() {
		List<Patient> patientsList = patientRepository.findAll();
		
		List<MinimalPatientDto> dtoList = patientMapper.patientsListToMinimalPatientDtoList(patientsList);
		
		return dtoList;
	}

	/**
	 * Creates a new patient, applying address validation and creation if one is provided. 
	 * @param dto
	 * @return
	 * @throws UncompleteAddressException
	 */
	@Transactional(rollbackOn = Exception.class)
	public PatientDto createPatient(CreatePatientDto dto) throws UncompleteAddressException {
		Patient newPatient = patientMapper.createPatientDtoToPatient(dto);

		if (dto.address() != null) {
			Address patientAddress = addAddressIfNotExists(dto.address());
			newPatient.setAddress(patientAddress);
		}

		return patientMapper.patientToPatientDto(patientRepository.save(newPatient));
	}
	
	/**
	 * Used for communication between microservices. 
	 * Updates a new patient, applying address validation and creation if one is provided. 
	 * @return {@link PatientDto} a representation of the updated patient
	 * @throws PatientNotFoundException
	 * @throws UncompleteAddressException
	 */
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
		
		if (dto.phone() != null) {
			if (!patient.getPhone().equalsIgnoreCase(dto.phone())) {
				System.out.println("phone has changed");
			}
			patient.setPhone(dto.phone());
		}

		return patientMapper.patientToPatientDto(patient);
	}

	/**
	 * 
	 * @param uuid
	 * @param dto
	 * @return
	 * @throws PatientNotFoundException
	 * @throws UncompleteAddressException
	 */
	@Transactional(rollbackOn = Exception.class)
	public PatientDto updatePatientByUuid(UUID uuid, UpdatePatientDto dto) throws PatientNotFoundException {
		Patient patient = patientRepository.findByUuid(uuid)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + uuid));
				
		if (!patient.getLastName().equalsIgnoreCase(dto.lastName())) {
			System.out.println("LastName changed");
			patient.setLastName(dto.lastName());
		}
		
		if (!patient.getFirstName().equalsIgnoreCase(dto.firstName())) {
			System.out.println("firstName changed");
			patient.setFirstName(dto.firstName());
		}
		
		if (!patient.getGender().equalsIgnoreCase(dto.gender())) {
			System.out.println("gender changed");
			patient.setGender(dto.gender());
		}
		
		if (dto.address() != null) {
			Address providedAddress = addAddressIfNotExists(dto.address());
			
			if (patient.getAddress() != null) {
				if (!patient.getAddress().equals(providedAddress)) {
					System.out.println("address changed");
					patient.setAddress(providedAddress);
				}
			}
		}
		
		if (dto.phone() != null) {
			if (!patient.getPhone().equalsIgnoreCase(dto.phone())) {
				System.out.println("phone has changed");
			}
			patient.setPhone(dto.phone());
		}
		
		return patientMapper.patientToPatientDto(patient);
	}
}
