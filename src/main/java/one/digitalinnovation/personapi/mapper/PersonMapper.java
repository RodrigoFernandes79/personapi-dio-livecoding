package one.digitalinnovation.personapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import one.digitalinnovation.personapi.model.Person;
import one.digitalinnovation.personapi.request.PersonDto;

@Mapper
public interface PersonMapper {
	
	PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
	
	@Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-mm-yyyy")
	Person toModel(PersonDto personDto);
	
	PersonDto toDTO(Person person);

	
	
}
