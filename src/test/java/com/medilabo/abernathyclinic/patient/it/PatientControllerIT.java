package com.medilabo.abernathyclinic.patient.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PatientControllerIT {
	@Autowired
	MockMvc mockMvc;

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
}
