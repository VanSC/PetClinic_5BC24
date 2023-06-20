package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundExeption;

public interface VetService {
	
	//find by name a Vet ( return 1 vet)
	List<Vet> findByName(String name);
	
	//create new Vet
	Vet create(Vet vet);
	
	//find a Vet by id
	Vet findById(Integer id) throws VetNotFoundExeption;
	
	//update a Vet
	Vet update(Vet vet);
	
	//return all vets
	List<Vet> findAll();
	
	//delete a Vet
	void delete(Integer id) throws VetNotFoundExeption;

}