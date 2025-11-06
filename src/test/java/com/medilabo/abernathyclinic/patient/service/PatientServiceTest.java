package com.medilabo.abernathyclinic.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.exception.UncompleteAddressException;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;
import com.medilabo.abernathyclinic.patient.service.address.AddressService;
import com.medilabo.abernathyclinic.patient.service.patient.PatientMapper;
import com.medilabo.abernathyclinic.patient.service.patient.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
	@Mock
	private PatientMapper mapper;
	
	@Mock
	PatientRepository patientRepository;
	
	@InjectMocks
	PatientService service;
	
	@Mock
	private AddressService addressService;
	
	@Test
	public void testFindPatientById_whenPatientExists_shouldReturnDto() throws PatientNotFoundException {
		// arrange
		Patient mockPatient = new Patient("Testlastname", "Testfirstname", LocalDate.of(2001, 12, 27), "F", null, null);
		PatientDto mockDto = mock(PatientDto.class);
		
		when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
		when(mockDto.lastName()).thenReturn("Testlastname");
		when(mapper.patientToPatientDto(mockPatient)).thenReturn(mockDto);
		
		// act
		PatientDto result = service.findPatientById(1L);
		
		// assert
		assertEquals("Testlastname", result.lastName());
	}
	
	@Test
	public void testFindPatientById_whenPatientDoesNotExists_shouldThrowException() throws PatientNotFoundException {
		// arrange				
		when(patientRepository.findById(2L)).thenReturn(Optional.empty());
		
		// act and assert
		assertThrows(PatientNotFoundException.class, () -> service.findPatientById(2L));	
	}
	
	@Test
	public void testcreatePatient_withoutAddress_shouldNotCallAddressService() throws UncompleteAddressException {
		// arrange		
		CreatePatientDto dto = new CreatePatientDto("Testfirstname", "Testlastname", 
				LocalDate.of(2001, 10, 01), "f", null, "004-901-019");
		
		UUID uuid = UUID.randomUUID();
		Patient patient = new Patient();
		PatientDto patientDto = new PatientDto(uuid, "Testlastname", "Testfirstname",
		        LocalDate.of(2001, 10, 1), "f", null, "004-901-019");

		
		when(mapper.createPatientDtoToPatient(dto)).thenReturn(patient);
		when(patientRepository.save(patient)).thenReturn(patient);
		when(mapper.patientToPatientDto(patient)).thenReturn(patientDto);
		
		//act
		service.createPatient(dto);
		
		// assert
		verify(addressService, never()).addAddress(dto.address());
	}
}
