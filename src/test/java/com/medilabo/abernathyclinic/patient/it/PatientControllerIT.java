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
			jsonPath("$.lastName").exists(),
			jsonPath("$.firstName").exists(),
			jsonPath("$.birthDate").exists(),
			jsonPath("$.gender").exists(),
			jsonPath("$.address.streetNumber").exists(),
			jsonPath("$.address.street").exists(),
			jsonPath("$.address.city").exists(),
			jsonPath("$.address.zip").isString(),
			jsonPath("$.uuid").exists(),
			jsonPath("$.id").doesNotExist()
		);
	}
	
	@Test
	public void testGetPatientById_withUnknownId_shouldReturnNotFound() throws Exception {
		int id = 999;
		
		mockMvc.perform(get("/api/patient/{id}", id))
		.andExpect(status().isNotFound());
	}
}
