package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.PetTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * 
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class PetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testFindAllPets() throws Exception {

		//int NRO_RECORD = 73;
		int ID_FIRST_RECORD = 1;

		this.mockMvc.perform(get("/pets"))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				//		    .andExpect(jsonPath("$", hasSize(NRO_RECORD)))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
	}
	

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testFindPetOK() throws Exception {

		String NAME_PET = "Leo";
		int TYPE_ID = 1;
		int OWNER_ID = 1;
		String BIRTH_DATE = "2000-09-07";

		mockMvc.perform(get("/pets/1"))  // Object must be BASIL
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME_PET)))
				.andExpect(jsonPath("$.typeId", is(TYPE_ID)))
				.andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
				.andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));
	}
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindPetKO() throws Exception {

		mockMvc.perform(get("/pets/666"))
				.andExpect(status().isNotFound());

	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testCreatePet() throws Exception {

		String NAME_PET = "Beethoven";
		int TYPE_ID = 1;
		int OWNER_ID = 1;
		String BIRTH_DATE = "2020-05-20";

		PetTO newPetTO = new PetTO();
		newPetTO.setName(NAME_PET);
		newPetTO.setTypeId(TYPE_ID);
		newPetTO.setOwnerId(OWNER_ID);
		newPetTO.setBirthDate(BIRTH_DATE);

		mockMvc.perform(post("/pets")
						.content(om.writeValueAsString(newPetTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				//.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(NAME_PET)))
				.andExpect(jsonPath("$.typeId", is(TYPE_ID)))
				.andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
				.andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));

	}
	
	/**@Test
	public void testUpdatePetById() throws Exception {
	    // Datos del objeto Pet a actualizar
	    int PET_ID = 1;
	    String UPDATED_NAME = "Max";
	    int UPDATED_TYPE_ID = 2;
	    int UPDATED_OWNER_ID = 2;
	    String UPDATED_BIRTH_DATE = "2019-10-15";

	    PetTO updatedPetTO = new PetTO();
	    updatedPetTO.setId(PET_ID);
	    updatedPetTO.setName(UPDATED_NAME);
	    updatedPetTO.setTypeId(UPDATED_TYPE_ID);
	    updatedPetTO.setOwnerId(UPDATED_OWNER_ID);
	    updatedPetTO.setBirthDate(UPDATED_BIRTH_DATE);

	    mockMvc.perform(put("/pets/{id}", PET_ID)
	            .content(om.writeValueAsString(updatedPetTO))
	            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(PET_ID)))
	            .andExpect(jsonPath("$.name", is(UPDATED_NAME)))
	            .andExpect(jsonPath("$.typeId", is(UPDATED_TYPE_ID)))
	            .andExpect(jsonPath("$.ownerId", is(UPDATED_OWNER_ID)))
	            .andExpect(jsonPath("$.birthDate", is(UPDATED_BIRTH_DATE)));
	}*/


	/**
     * 
     * @throws Exception
     */
	@Test
	public void testDeletePet() throws Exception {

		String NAME_PET = "Beethoven3";
		int TYPE_ID = 1;
		int OWNER_ID = 1;
		String BIRTH_DATE = "2020-05-20";

		PetTO newPetTO = new PetTO();
		newPetTO.setName(NAME_PET);
		newPetTO.setTypeId(TYPE_ID);
		newPetTO.setOwnerId(OWNER_ID);
		newPetTO.setBirthDate(BIRTH_DATE);

		ResultActions mvcActions = mockMvc.perform(post("/pets")
						.content(om.writeValueAsString(newPetTO))
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/pets/" + id ))
				/*.andDo(print())*/
				.andExpect(status().isOk());
	}

}
    