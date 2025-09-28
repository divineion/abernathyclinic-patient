package com.medilabo.abernathyclinic.patient.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.abernathyclinic.patient.dto.AddressDto;
import com.medilabo.abernathyclinic.patient.dto.CreatePatientDto;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PatientControllerIT {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testGetPatientById_shouldReturnPatientDto() throws Exception {
		int id = 1;
		
		mockMvc.perform(get("/api/patient/{id}", id))
		.andExpectAll(
			status().isOk(),
			jsonPath("$.lastName").isString(),
			jsonPath("$.firstName").isString(),
			jsonPath("$.birthDate").isString(),
			jsonPath("$.gender").isString(),
			jsonPath("$.address.streetNumber").isString(),
			jsonPath("$.address.street").isString(),
			jsonPath("$.address.city").isString(),
			jsonPath("$.address.zip").isString(),
			jsonPath("$.phone").isString(),
			jsonPath("$.uuid").isString(),
			jsonPath("$.id").doesNotExist()
		);
	}
	
	@Test
	public void testGetPatientById_withUnknownId_shouldReturnNotFound() throws Exception {
		int id = 999;
		
		mockMvc.perform(get("/api/patient/{id}", id))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAllPatient_shouldReturnDtoList() throws Exception {
		mockMvc.perform(get("/api/patients"))
		.andExpectAll(
			status().isOk(),
			jsonPath("$").isArray(),
			jsonPath("$.[0].uuid").isString(),
			jsonPath("$.[0].firstName").isString(),
			jsonPath("$.[0].lastName").isString(),
			jsonPath("$.[0].birthDate").isString(),
			jsonPath("$.[0].gender").isString()
		);
	}
	
	@Test
	public void testCreatePatient_shouldReturnCreated() throws Exception {
		AddressDto addressDto = new AddressDto("1", "Dream str", "Sringfield", "45890");
		CreatePatientDto dto = new CreatePatientDto("Testfirstname", "Testlastname", LocalDate.of(2001, 10, 01), "f", addressDto, "004-901-019");
		
		mockMvc.perform(post("/api/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpectAll(
				status().isCreated(),
				jsonPath("$.uuid").isString(),
				jsonPath("$.firstName").isString(),
				jsonPath("$.lastName").isString(),
				jsonPath("$.birthDate").isString(),
				jsonPath("$.gender").isString(),
				jsonPath("$.address.streetNumber").isString(),
				jsonPath("$.address.street").isString(),
				jsonPath("$.address.city").isString(),
				jsonPath("$.address.zip").isString(),
				jsonPath("$.phone").isString()
			);
	}
	
	@Test
	public void testCreatePatient_withoutAddress_shouldReturnCreated() throws Exception {
		CreatePatientDto dto = new CreatePatientDto("Testfirstname", "Testlastname", LocalDate.of(2001, 10, 01), "f", null, "004-901-019");
	
		mockMvc.perform(post("/api/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpectAll(
				status().isCreated(),
				jsonPath("$.uuid").isString(),
				jsonPath("$.firstName").isString(),
				jsonPath("$.lastName").isString(),
				jsonPath("$.gender").isString(),
				jsonPath("$.birthDate").isString(),
				jsonPath("$.address").value(Matchers.nullValue()),
				jsonPath("$.phone").isString()
		);
	}

	@Test
	public void testCreatePatient_withoutPhone_shouldReturnCreated() throws Exception {
		AddressDto addressDto = new AddressDto("1", "Dream str", "Sringfield", "45890");
		CreatePatientDto dto = new CreatePatientDto("Testfirstname", "Testlastname", LocalDate.of(2001, 10, 01), "f", addressDto, null);
		
		mockMvc.perform(post("/api/patient")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
		.andExpectAll(
				status().isCreated(),
				jsonPath("$.uuid").isString(),
				jsonPath("$.firstName").isString(),
				jsonPath("$.lastName").isString(),
				jsonPath("$.gender").isString(),
				jsonPath("$.address.streetNumber").isString(),
				jsonPath("$.address.street").isString(),
				jsonPath("$.address.city").isString(),
				jsonPath("$.address.zip").isString(),
				jsonPath("$.phone").value(Matchers.nullValue())
				);
	}
	
	@Test
	public void testCreatePatient_withInvalidFields_shouldReturnBadRequest() throws Exception {
		CreatePatientDto dto = new CreatePatientDto("T", "Testlastname", LocalDate.of(2001, 10, 01), "f", null, null);
		
		mockMvc.perform(post("/api/patient")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isBadRequest());
	}
}
