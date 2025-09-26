package com.medilabo.abernathyclinic.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.exception.PatientNotFoundException;
import com.medilabo.abernathyclinic.patient.repository.PatientRepository;
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
	
	@Test
	public void testFindPatientById_whenPatientExists_shouldReturnDto() throws PatientNotFoundException {
		// arrange
		Patient mockPatient = new Patient("Testlastname", "Testfirstname", LocalDate.of(2001, 12, 27), "F", null, null);
		PatientDto mockDto = mock(PatientDto.class);
		
		when(patientRepository.findById(1L)).thenReturn(Optional.of(mockPatient));
		when(mockDto.lastName()).thenReturn("Testlastname");
		when(mapper.patientToPatientDto(mockPatient)).thenReturn(mockDto);
		
		// act
		PatientDto result = service.findById(1L);
		
		// assert
		assertEquals("Testlastname", result.lastName());
	}
	
	@Test
	public void testFindPatientById_whenPatientDoesNotExists_shouldThrowException() throws PatientNotFoundException {
		// arrange				
		when(patientRepository.findById(2L)).thenReturn(Optional.empty());
		
		// act and assert
		assertThrows(PatientNotFoundException.class, () -> service.findById(2L));	
	}
}
