package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.PetTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.mapper.PetMapper;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.services.PetService;
import com.tecsup.petclinic.util.TObjectCreator;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

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
public class PetControllerMockitoTest {

    private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository petRepository;

	@MockBean
	private PetService petService;

	PetMapper mapper = Mappers.getMapper(PetMapper.class);

	@BeforeEach
	void setUp() {
		// Initialize RestAssured
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@AfterEach
	void tearDown() {
	}
	@Test
	public void testFindAllPets() throws Exception {

		int NRO_RECORD = 5;
		int ID_FIRST_RECORD = 1;

		List<PetTO> petTOs  = TObjectCreator.getAllPetTOs();

		List<Pet> pets  = this.mapper.toPetList(petTOs);

		Mockito.when(petService.findAll())
				.thenReturn(pets);

		this.mockMvc.perform(get("/pets"))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.size()", is(NRO_RECORD)))
				.andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));

	}
	

	/**
	 * 
	 * @throws Exception
	 * 
	 */
	@Test
	public void testFindPetOK() throws Exception {

		PetTO petTO  = TObjectCreator.getPetTO();

		Pet pet  = this.mapper.toPet(petTO);

		Mockito.when(petService.findById(pet.getId()))
				.thenReturn(pet);

		mockMvc.perform(get("/pets/1"))  // Object must be BASIL
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(petTO.getId())))
				.andExpect(jsonPath("$.name", is(petTO.getName())))
				.andExpect(jsonPath("$.typeId", is(petTO.getTypeId())))
				.andExpect(jsonPath("$.ownerId", is(petTO.getOwnerId())))
				.andExpect(jsonPath("$.birthDate", is(petTO.getBirthDate())));
	}


	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFindPetKO() throws Exception {

		Integer ID_NOT_EXIST = 666;

		Mockito.when(this.petService.findById(ID_NOT_EXIST))
				.thenThrow(new PetNotFoundException("Record not found...!"));

		mockMvc.perform(get("/pets/666"))
				.andExpect(status().isNotFound());
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void testCreatePet() throws Exception {

		PetTO newPetTO  = TObjectCreator.newPetTO();

		Pet newPet  = this.mapper.toPet(newPetTO);

		Mockito.when(petService.create(newPet))
				.thenReturn(newPet);

		mockMvc.perform(post("/pets")
				.content(om.writeValueAsString(newPetTO))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				//.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is(newPetTO.getName())))
				.andExpect(jsonPath("$.typeId", is(newPetTO.getTypeId())))
				.andExpect(jsonPath("$.ownerId", is(newPetTO.getOwnerId())))
				.andExpect(jsonPath("$.birthDate", is(newPetTO.getBirthDate())));

	}


	/**
     * 
     * @throws Exception
     */
	@Test
	public void testDeletePet() throws Exception {

		// ------------ Create ---------------

		PetTO newPetTO  = TObjectCreator.newPetTOForDelete();

		Pet newPet  = this.mapper.toPet(newPetTO);

		Mockito.when(petService.create(newPet))
				.thenReturn(newPet);

		ResultActions mvcActions = mockMvc.perform(post("/pets")
				.content(om.writeValueAsString(newPetTO))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());

		String response = mvcActions.andReturn().getResponse().getContentAsString();


		// ------------ Delete ---------------

		Mockito.doNothing().when(this.petService).delete(newPet.getId());

		Integer id = JsonPath.parse(response).read("$.id");

		mockMvc.perform(delete("/pets/" + id ))
				/*.andDo(print())*/
				.andExpect(status().isOk());
	}
    
}
    