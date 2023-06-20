package com.tecsup.petclinic.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * @author jgomezm
 *
 */
@Entity(name = "pets")
@Data
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Column(name = "type_id")
	private int typeId;
	@Column(name = "owner_id")
	private int ownerId;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date")
	private Date birthDate;
	
	public Pet() {
	}

	public Pet(Integer id, String name, int type_id, int owner_id, Date birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.typeId = type_id;
		this.ownerId = owner_id;
		this.birthDate = birthDate;

	}

	public Pet(String name, int type_id, int owner_id, Date birthDate) {
		super();
		this.name = name;
		this.typeId = type_id;
		this.ownerId = owner_id;
		this.birthDate = birthDate;
	}
}