package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundExeption;
import com.tecsup.petclinic.repositories.VetRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VetServiceIpml implements VetService {
	
	@Autowired
	VetRepository vetRepository;
	
	@Override
	public List<Vet> findByName(String name) {
		List<Vet> vets = vetRepository.findByName(name);
		vets.stream().forEach(vet -> log.info(""+vet));
		return vets;
	}

	@Override
	public Vet create(Vet vet) {
		return vetRepository.save(vet);
	}

	@Override
	public Vet update(Vet vet) {
		return vetRepository.save(vet);
	}

	public Vet findById(Integer id) throws VetNotFoundExeption {
		Optional<Vet> vet = vetRepository.findById(id);
		if(!vet.isPresent()) 
			throw new VetNotFoundExeption("vet not found");
		return vet.get();
	}

	@Override
	public List<Vet> findAll() {
		return vetRepository.findAll();
	}

	@Override
	public void delete(Integer id) throws VetNotFoundExeption {
		Vet vet = findById(id);
		vetRepository.delete(vet);
	}
}
