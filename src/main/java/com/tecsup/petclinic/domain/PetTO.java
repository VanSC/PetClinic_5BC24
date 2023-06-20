package com.tecsup.petclinic.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetTO {

	private Integer id;
	
	private String name;
	
	private int typeId;

	private int ownerId;

	private String birthDate;

}
