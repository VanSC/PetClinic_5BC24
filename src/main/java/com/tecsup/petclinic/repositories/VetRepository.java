package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Vet;

@Repository
public interface VetRepository extends CrudRepository<Vet, Integer> {
	
	//Fetch by vet's name
	List<Vet> findByName(String name);
	
	//fetch all vets
	@Override
	List<Vet> findAll();
	
}
