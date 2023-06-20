package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.domain.PetTO;
import com.tecsup.petclinic.entities.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public interface PetMapper {

	PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

	//@Mapping(target = "name", source = "name")
	@Mapping(source = "birthDate", target = "birthDate")
	Pet toPet(PetTO petTO);

	default Date stringToDate(String dateStr) {

		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	@Mapping(source = "birthDate", target = "birthDate")
	PetTO toPetTO(Pet pet);

	default String dateToString(Date date) {

		if (date != null ) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.format(date);
		} else {
			return "";
		}

	}

	List<PetTO> toPetTOList(List<Pet> petList);

	List<Pet> toPetList(List<PetTO> petTOList);
}
