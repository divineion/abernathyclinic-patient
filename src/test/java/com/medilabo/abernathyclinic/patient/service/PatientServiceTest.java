package com.medilabo.abernathyclinic.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
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

import com.medilabo.abernathyclinic.patient.dto.AddressDto;
import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;
import com.medilabo.abernathyclinic.patient.dto.PatientDto;
import com.medilabo.abernathyclinic.patient.dto.UpdatePatientDto;
import com.medilabo.abernathyclinic.patient.entity.Address;
import com.medilabo.abernathyclinic.patient.entity.City;
import com.medilabo.abernathyclinic.patient.entity.Patient;
import com.medilabo.abernathyclinic.patient.entity.Street;
import com.medilabo.abernathyclinic.patient.exception.IncompleteAddressException;
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
	
	private final static String STREET_NAME = "Test str.";
	private final static String CITY_NAME = "Testcity";
	private final static String ZIP = "00000";
	
	private final static City CITY = new City(CITY_NAME, ZIP); 
	private final static Street STREET = new Street(STREET_NAME, CITY);
	private final static String STREET_NUMBER = "6";

	
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
	public void testcreatePatient_withoutAddress_shouldNotCallAddressService() throws IncompleteAddressException {
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
	
	/**
	 * Ensures addresses are not persisted if they not change on patient update. 
	 * @throws PatientNotFoundException
	 * @throws IncompleteAddressException
	 */
	@Test
	public void testUpdatePatient_withSameAddress_shouldNotCallAddressService() throws PatientNotFoundException, IncompleteAddressException {
		// arrange
		Address address = new Address(STREET_NUMBER, STREET);
		Patient patient = new Patient("AnyFirstName", "AnyLastName", LocalDate.of(2006, 6, 26), "F", address, null);
		
		AddressDto addressDto = new AddressDto(STREET_NUMBER, STREET_NAME, CITY_NAME, ZIP);
		UpdatePatientDto dto = new UpdatePatientDto("AnyFirstName", "AnyLastName", addressDto, null);
		
		when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
		
		//act
		service.updatePatient(1L, dto);
		
		// assert
		verify(addressService, never()).addAddress(addressDto);
	}
	
	/**
	 * Ensures address is persisted on patient update. 
	 * @throws PatientNotFoundException
	 * @throws IncompleteAddressException
	 */
	@Test
	public void testUpdatePatient_withUpdatedAddress_shouldCallAddressService() throws PatientNotFoundException, IncompleteAddressException {
		// arrange
		Address address = new Address(STREET_NUMBER, STREET);
		Patient patient = new Patient("AnyFirstName", "AnyLastName", LocalDate.of(2006, 6, 26), "F", address, null);
		
		AddressDto addressDto = new AddressDto(STREET_NUMBER, "New Street", CITY_NAME, ZIP);
		UpdatePatientDto dto = new UpdatePatientDto("AnyFirstName", "AnyLastName", addressDto, null);
		
		UUID uuid = UUID.randomUUID();
		
		when(patientRepository.findByUuid(uuid)).thenReturn(Optional.of(patient));
		
		//act
		service.updatePatientByUuid(uuid, dto);
		
		// assert
		verify(addressService, atLeastOnce()).addAddress(addressDto);
	}
	
	@Test
	public void testCreatePatient_withPartialAddress_shouldThrowException() throws IncompleteAddressException {
		// arrange
		AddressDto addressDto = new AddressDto(STREET_NUMBER, "", CITY_NAME, ZIP);
		CreatePatientDto dto = new CreatePatientDto("AnyFirstName", "AnyLastName", LocalDate.of(2000, 01, 01), "F", addressDto, null);
						
		//act and assert
		assertThrows(IncompleteAddressException.class, () -> service.createPatient(dto));
	}
}
