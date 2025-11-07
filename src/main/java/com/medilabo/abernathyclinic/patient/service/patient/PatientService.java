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
import com.medilabo.abernathyclinic.patient.exception.IncompleteAddressException;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;
import com.medilabo.abernathyclinic.patient.service.address.AddressService;

import jakarta.transaction.Transactional;

@Service
public class PatientService {
	private final PatientRepository patientRepository;
	private final PatientMapper patientMapper;
	private final AddressService addressService;
	
	public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, AddressService addressService) {
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
	 * @throws IncompleteAddressException
	 */
	@Transactional(rollbackOn = Exception.class)
	public PatientDto createPatient(CreatePatientDto dto) throws IncompleteAddressException {
		Patient newPatient = patientMapper.createPatientDtoToPatient(dto);
		
		if (dto.address() == null || isEmptyAddress(dto.address())) {
			newPatient.setAddress(null);
		} else {
			if (isPartialAddress(dto.address())) {
				throw new IncompleteAddressException(ApiMessages.INCOMPLETE_ADDRESS);
			} else {
				newPatient.setAddress(addAddressIfNotExists(dto.address()));
			}
		}
		
		if (dto.phone() == null || dto.phone().isBlank()) {				
			newPatient.setPhone(null);
		} else {
			newPatient.setPhone(dto.phone());
		}
		
		return patientMapper.patientToPatientDto(patientRepository.save(newPatient));
	}
	
	/**
	 * Used for communication between microservices. 
	 * Updates a new patient, applying address validation and creation if one is provided. 
	 * @return {@link PatientDto} a representation of the updated patient
	 * @throws PatientNotFoundException
	 * @throws IncompleteAddressException
	 */
	@Transactional(rollbackOn = Exception.class)
	public PatientDto updatePatient(Long id, UpdatePatientDto dto) throws PatientNotFoundException, IncompleteAddressException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + id));
		
		if (dto.address() == null || isEmptyAddress(dto.address())) {
			patient.setAddress(null);
		} else {
			if (isPartialAddress(dto.address())) {
				throw new IncompleteAddressException(ApiMessages.INCOMPLETE_ADDRESS);
			}
								
			if (!isSameAddress(dto.address(), patient.getAddress())) {
				patient.setAddress(addAddressIfNotExists(dto.address()));
			}
		}
		
		if (dto.phone() == null || dto.phone().isBlank()) {				
			patient.setPhone(null);
		} else {
			patient.setPhone(dto.phone());
		}
		
		if (!patient.getLastName().equalsIgnoreCase(dto.lastName())) {
			patient.setLastName(dto.lastName());
		}
		
		if (!patient.getFirstName().equalsIgnoreCase(dto.firstName())) {
			patient.setFirstName(dto.firstName());
		}
		
		if (!patient.getGender().equalsIgnoreCase(dto.gender())) {
			patient.setGender(dto.gender());
		}

		return patientMapper.patientToPatientDto(patient);
	}

	/**
	 * 
	 * @param uuid
	 * @param dto
	 * @return
	 * @throws PatientNotFoundException
	 * @throws IncompleteAddressException
	 */
	@Transactional(rollbackOn = Exception.class)
	public PatientDto updatePatientByUuid(UUID uuid, UpdatePatientDto dto) throws PatientNotFoundException, IncompleteAddressException {
		Patient patient = patientRepository.findByUuid(uuid)
				.orElseThrow(() -> new PatientNotFoundException(ApiMessages.PATIENT_NOT_FOUND + uuid));
		
		if (dto.address() == null || isEmptyAddress(dto.address())) {
			patient.setAddress(null);
		} else {
			if (isPartialAddress(dto.address())) {
				throw new IncompleteAddressException(ApiMessages.INCOMPLETE_ADDRESS);
			} 
			
			if (!isSameAddress(dto.address(), patient.getAddress())) {
				patient.setAddress(addAddressIfNotExists(dto.address()));
			}
		}
		
		if (dto.phone() == null || dto.phone().isBlank()) {				
			patient.setPhone(null);
		} else {
			patient.setPhone(dto.phone());
		}
				
		if (!patient.getLastName().equalsIgnoreCase(dto.lastName())) {
			patient.setLastName(dto.lastName());
		}
		
		if (!patient.getFirstName().equalsIgnoreCase(dto.firstName())) {
			patient.setFirstName(dto.firstName());
		}
		
		if (!patient.getGender().equalsIgnoreCase(dto.gender())) {
			patient.setGender(dto.gender());
		}
		
		return patientMapper.patientToPatientDto(patient);
	}
}
