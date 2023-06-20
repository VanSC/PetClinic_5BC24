package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
public class PetServiceTest {

    @Autowired
    private PetService petService;

    /**
     *
     */
    @Test
    public void testFindPetById() {

        Integer ID = 1;
        String NAME = "Leo";
        Pet pet = null;

        try {
            pet = this.petService.findById(ID);
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + pet);
        assertEquals(NAME, pet.getName());

    }

    /**
     *
     */
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";
        int SIZE_EXPECTED = 1;

        List<Pet> pets = this.petService.findByName(FIND_NAME);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByTypeId(TYPE_ID);

        assertEquals(SIZE_EXPECTED, pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;
        int SIZE_EXPECTED = 2;

        List<Pet> pets = this.petService.findByOwnerId(OWNER_ID);

        assertEquals(SIZE_EXPECTED, pets.size());

    }

    /**
     * To get ID generate , you need
     * setup in id primary key in your
     * entity this annotation :
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     */
    @Test
    public void testCreatePet() {

        String PET_NAME = "Ponky";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        Pet pet = new Pet(PET_NAME, 1, 1, null);

        Pet petCreated = this.petService.create(pet);

        log.info("PET CREATED :" + petCreated);

        assertNotNull(petCreated.getId());
        assertEquals(PET_NAME, petCreated.getName());
        assertEquals(OWNER_ID, petCreated.getOwnerId());
        assertEquals(TYPE_ID, petCreated.getTypeId());

    }


    /**
     *
     */
    @Test
    public void testUpdatePet() {

        String PET_NAME = "Bear";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        String UP_PET_NAME = "Bear2";
        int UP_OWNER_ID = 2;
        int UP_TYPE_ID = 2;

        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID, null);

        // ------------ Create ---------------

        log.info(">" + pet);
        Pet petCreated = this.petService.create(pet);
        log.info(">>" + petCreated);

        // ------------ Update ---------------

        // Prepare data for update
        petCreated.setName(UP_PET_NAME);
        petCreated.setOwnerId(UP_OWNER_ID);
        petCreated.setTypeId(UP_TYPE_ID);

        // Execute update
        Pet upgradePet = this.petService.update(petCreated);
        log.info(">>>>" + upgradePet);

        //            EXPECTED        ACTUAL
        assertEquals(UP_PET_NAME, upgradePet.getName());
        assertEquals(UP_OWNER_ID, upgradePet.getTypeId());
        assertEquals(UP_TYPE_ID, upgradePet.getOwnerId());
    }

    /**
     *
     */
    @Test
    public void testDeletePet() {

        String PET_NAME = "Bird";
        int OWNER_ID = 1;
        int TYPE_ID = 1;

        // ------------ Create ---------------

        Pet pet = new Pet(PET_NAME, OWNER_ID, TYPE_ID, null);
        pet = this.petService.create(pet);
        log.info("" + pet);

        // ------------ Delete ---------------

        try {
            this.petService.delete(pet.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validation ---------------

        try {
            this.petService.findById(pet.getId());
            assertTrue(false);
        } catch (PetNotFoundException e) {
            assertTrue(true);
        }

    }
}
