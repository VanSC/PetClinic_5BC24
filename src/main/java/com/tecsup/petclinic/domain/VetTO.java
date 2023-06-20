package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Grupo5B

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetTO {
	private Integer id;
	private String name;
	private String last_name;
}
