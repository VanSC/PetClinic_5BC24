package com.tecsup.petclinic.webs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;

import com.tecsup.petclinic.domain.VetTO;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class VetControllerTest {
	
    private static final ObjectMapper om = new ObjectMapper();

	
	@Autowired
	private MockMvc mockMvc;
	
	//Find all vets
	@Test
	public void testFindAllPets() throws Exception {

		//int NRO_RECORD = 73;
		int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/vets"))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				//		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	
	//create new Vet
	@Test
	void testCreateVet() throws Exception{
		String VET_NAME = "Kevin";
		String VET_LASTNAME = "Karl";
		
		VetTO newVetTo = new VetTO();
		
		newVetTo.setName(VET_NAME);
		newVetTo.setLast_name(VET_LASTNAME);
		
		mockMvc.perform(post("/vets")
				.content(om.writeValueAsString(newVetTo))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", is(VET_NAME)))
		.andExpect(jsonPath("$.last_name", is(VET_LASTNAME)));
		
	}
	
	//update a Vet
	@Test
	void testUpdateVetById() throws Exception {
	    // Datos del veterinario a actualizar
	    int VET_ID = 5;
	    String UPDATED_NAME = "Vanessa";
	    String UPDATED_LASTNAME = "Surco";

	    VetTO updatedVetTo = new VetTO();
	    updatedVetTo.setId(VET_ID);
	    updatedVetTo.setName(UPDATED_NAME);
	    updatedVetTo.setLast_name(UPDATED_LASTNAME);

	    mockMvc.perform(put("/vets/{id}", VET_ID)
	            .content(om.writeValueAsString(updatedVetTo))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(VET_ID)))
	            .andExpect(jsonPath("$.name", is(UPDATED_NAME)))
	            .andExpect(jsonPath("$.last_name", is(UPDATED_LASTNAME)));
	}
	
	//Delete a Vet by id
	@Test
	public void testDeletePet() throws Exception {

		String VET_NAME = "Alexa";
		String VET_LASTNAME = "Surco";

		VetTO newVetTO = new VetTO();
		newVetTO.setName(VET_NAME);
		newVetTO.setLast_name(VET_LASTNAME);

		ResultActions mvcActions = mockMvc.perform(post("/vets")
						.content(om.writeValueAsString(newVetTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/vets/" + id ))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	//find a Vet by name
	@Test
	void testFindByName() throws Exception {
			
		String NAME_VET = "Alexandro";
	    String LAST_NAME_VET = "Smith";

	    mockMvc.perform(get("/vetsname/Alexandro"))
	            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andDo(print())
	            .andExpect(jsonPath("$[0].name", is(NAME_VET)))
	            .andExpect(jsonPath("$[0].last_name", is(LAST_NAME_VET)));
	}
}
