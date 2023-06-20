package com.tecsup.petclinic.services;


import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.repositories.PetRepository;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PetServiceMockitoTest {

    private PetService petService;

    @Mock //Bean
    private PetRepository repository;

    @BeforeEach
    void setUp() {
        this.petService = new PetServiceImpl(this.repository);
    }

    /**
     *
     */
    @Test
    public void testFindPetById() {

        Pet petExpected = TObjectCreator.getPet();

        Mockito.when(this.repository.findById(petExpected.getId()))
                .thenReturn((Optional.of(petExpected)));
        try {
            petExpected = this.petService.findById(petExpected.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        log.info("" + petExpected);
        assertEquals(petExpected.getName(), petExpected.getName());

    }

    /**
     *
     */
    @Test
    public void testFindPetByName() {

        String FIND_NAME = "Leo";

        List<Pet> petsExpected = TObjectCreator.getPetsForFindByName();

        Mockito.when(this.repository.findByName(FIND_NAME))
                .thenReturn(petsExpected);

        List<Pet> pets = this.petService.findByName(FIND_NAME);

        assertEquals(petsExpected.size(), pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByTypeId() {

        int TYPE_ID = 5;

        List<Pet> petsExpected = TObjectCreator.getPetsForFindByTypeId();

        Mockito.when(this.repository.findByTypeId(TYPE_ID))
                .thenReturn(petsExpected);

        List<Pet> pets = this.petService.findByTypeId(TYPE_ID);

        assertEquals(petsExpected.size(), pets.size());
    }

    /**
     *
     */
    @Test
    public void testFindPetByOwnerId() {

        int OWNER_ID = 10;

        List<Pet> petsExpected = TObjectCreator.getPetsForFindByOwnerId();

        Mockito.when(this.repository.findByOwnerId(OWNER_ID))
                .thenReturn(petsExpected);

        List<Pet> pets = this.petService.findByOwnerId(OWNER_ID);

        assertEquals(petsExpected.size(), pets.size());

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

        Pet newPet = TObjectCreator.newPet();
        Pet newCreatePet = TObjectCreator.newPetCreated();

        Mockito.when(this.repository.save(newPet))
                .thenReturn(newCreatePet);

        Pet petCreated = this.petService.create(newPet);

        log.info("Pet created : {}" , petCreated);

        assertNotNull(petCreated.getId());
        assertEquals(newCreatePet.getName(), petCreated.getName());
        assertEquals(newCreatePet.getOwnerId(), petCreated.getOwnerId());
        assertEquals(newCreatePet.getTypeId(), petCreated.getTypeId());

    }


    /**
     *
     */
    @Test
    public void testUpdatePet() {

        String UP_PET_NAME = "Bear2";
        int UP_OWNER_ID = 2;
        int UP_TYPE_ID = 2;

        Pet newPet = TObjectCreator.newPetForUpdate();
        Pet newPetCreate = TObjectCreator.newPetCreatedForUpdate();

        // ------------ Create ---------------

        Mockito.when(this.repository.save(newPet))
                .thenReturn(newPetCreate);

        Pet petCreated = this.petService.create(newPet);
        log.info("{}" , petCreated);

        // ------------ Update ---------------

        // Prepare data for update
        petCreated.setName(UP_PET_NAME);
        petCreated.setOwnerId(UP_OWNER_ID);
        petCreated.setTypeId(UP_TYPE_ID);

        // Create
        Mockito.when(this.repository.save(petCreated))
                .thenReturn(petCreated);

        // Execute update
        Pet upgradePet = this.petService.update(petCreated);
        log.info("{}" + upgradePet);

        //            EXPECTED           ACTUAL
        assertEquals(UP_PET_NAME, upgradePet.getName());
        assertEquals(UP_OWNER_ID, upgradePet.getTypeId());
        assertEquals(UP_TYPE_ID, upgradePet.getOwnerId());
    }

    /**
     *
     */
    @Test
    public void testDeletePet() {

        Pet newPet = TObjectCreator.newPetForDelete();
        Pet newPetCreate = TObjectCreator.newPetCreatedForDelete();

        // ------------ Create ---------------

        Mockito.when(this.repository.save(newPet))
                .thenReturn(newPetCreate);

        Pet petCreated = this.petService.create(newPet);
        log.info("{}" ,petCreated);

        // ------------ Delete ---------------

        Mockito.doNothing().when(this.repository).delete(newPetCreate);
        Mockito.when(this.repository.findById(newPetCreate.getId()))
                .thenReturn(Optional.of(newPetCreate));

        try {
            this.petService.delete(petCreated.getId());
        } catch (PetNotFoundException e) {
            fail(e.getMessage());
        }

        // ------------ Validate ---------------

        Mockito.when(this.repository.findById(newPetCreate.getId()))
                .thenReturn(Optional.ofNullable(null));

        try {
            this.petService.findById(petCreated.getId());
            assertTrue(false);
        } catch (PetNotFoundException e) {
            assertTrue(true);
        }

    }

}