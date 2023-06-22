package com.tecsup.petclinic.services;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.exception.VetNotFoundExeption;

@SpringBootTest
@Slf4j
public class VetServiceTest {
	
	@Autowired
	private VetService vetService;
	
	/** 
	 * find by name a vet
	 * 
	 * */
	@Test
	public void testFindVetByName() {
		String NAME = "Manuel";
		int SIZE_EXPECTED = 1;
		List<Vet> vets = this.vetService.findByName(NAME);
		assertEquals(SIZE_EXPECTED, vets.size());
	}
	
	/** 
	 * Create vet
	 * 
	 * */
	
	@Test
    public void testCreateVet() {

        String NAME_VET = "Royer";
        String VET_LASTNAME = "Hyle";

        Vet vet = new Vet(NAME_VET,VET_LASTNAME);

        Vet vetCreated = this.vetService.create(vet);

        log.info("VET CREATED :" + vetCreated);

        assertNotNull(vetCreated.getId());
        assertEquals(NAME_VET, vetCreated.getName());
        assertEquals(VET_LASTNAME, vetCreated.getLast_name());
    }
	
	/** 
	 * Update a vet (in this test 
	 * its creating a new Vet 
	 * an updating the same 
	 * register
	 * */
	@Test
    public void testUpdateVet() {

		String NAME_VET = "Miguel";
        String VET_LASTNAME = "Hyle";

        String UP_NAME_VET = "Manuel";
        String UP_VET_LASTNAME = "Arcangel";

        Vet vet = new Vet(NAME_VET,VET_LASTNAME);

        // ------------ Create ---------------

        log.info(">" + vet);
        Vet vetCreated = this.vetService.create(vet);
        log.info(">>" + vetCreated);

        // ------------ Update ---------------

        // Prepare data for update
        vetCreated.setName(UP_NAME_VET);
        vetCreated.setLast_name(UP_VET_LASTNAME);

        // Execute update
        Vet upgradeVet = this.vetService.update(vetCreated);
        log.info(">>>>" + upgradeVet);

        //            EXPECTED        UPDATED
        assertEquals(UP_NAME_VET, upgradeVet.getName());
        assertEquals(UP_VET_LASTNAME, upgradeVet.getLast_name());
    }
	
	/** 
	 * Delete a Vet bu id
	 **cretae and delete
	 * */
	@Test
    public void testDeleteVet() {

		String NAME_VET = "Lucero";
        String VET_LASTNAME = "Hyle";

     // ------------ Create ---------------
        
        Vet vet = new Vet(NAME_VET,VET_LASTNAME);
        
        log.info(">" + vet);
        Vet vetCreated = this.vetService.create(vet);
        log.info(">>" + vetCreated);

        // ------------ Delete ---------------

        try {
            this.vetService.delete(vet.getId());
        } catch (VetNotFoundExeption e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.vetService.findById(vet.getId());
            assertTrue(false);
        } catch (VetNotFoundExeption e) {
            assertTrue(true);
        }

    }
}
