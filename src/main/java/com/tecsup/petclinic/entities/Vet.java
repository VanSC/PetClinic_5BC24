package com.tecsup.petclinic.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity(name="vets")
@Data
public class Vet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="first_name")
	private String name;
	@Column(name="last_name")
	private String last_name;
	
	public Vet() {
		
	}

	public Vet(Integer id, String name, String last_name) {
		super();
		this.id = id;
		this.name = name;
		this.last_name = last_name;
	}

	public Vet(String name, String last_name) {
		super();
		this.name = name;
		this.last_name = last_name;
	}
}

