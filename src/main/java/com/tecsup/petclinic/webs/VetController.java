package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.domain.PetTO;
import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundExeption;
import com.tecsup.petclinic.services.VetService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class VetController {
	
	@Autowired
	private VetService vetService;
	
	//list vets
	@GetMapping(value = "/vets")
	public ResponseEntity<List<Vet>> findAllPets() {

		List<Vet> vets = (List<Vet>) vetService.findAll();
		log.info("pets: " + vets);
		vets.forEach(item -> log.info("Pet >>  {} ", item));

		return ResponseEntity.ok(vets);

	}
	
	//Registrar un Vet
	@PostMapping(value = "/vets")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Vet> create(@RequestBody Vet vetTO) {
		Vet newVet = vetService.create(vetTO);
		return  ResponseEntity.status(HttpStatus.CREATED).body(newVet);
	}
	
	//find by id a vet
	@GetMapping("/vets/{id}")
	public ResponseEntity<Vet> findById(@PathVariable Integer id) throws VetNotFoundExeption{
		Vet vet = vetService.findById(id);
		return ResponseEntity.ok(vet);
	}
	
	//update a Vet
	@PutMapping(value = "/vets/{id}")
	public ResponseEntity<Vet> update(@RequestBody VetTO vetTO,@PathVariable Integer id) throws VetNotFoundExeption{
		
		
		Vet updateVet = vetService.findById(id);
		
		updateVet.setName(vetTO.getName());
		updateVet.setLast_name(vetTO.getLast_name());
		
		vetService.update(updateVet);
		
		return ResponseEntity.ok(updateVet);
	}
	
	//encontrar un Vet por nombre
	@GetMapping(value = "/vetsname/{name}")
	public ResponseEntity<List<Vet>> findByName(@PathVariable String name){
		List<Vet> vet_name = vetService.findByName(name);
		return ResponseEntity.ok(vet_name);
	}
	
	//delete a vet by id
	@DeleteMapping(value="/vets/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		try {
			vetService.delete(id);
			return ResponseEntity.ok("Vet Delete ID : "+id);
		} catch (VetNotFoundExeption e) {
			return ResponseEntity.notFound().build();
		}
	}
}
