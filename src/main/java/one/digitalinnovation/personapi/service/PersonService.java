package one.digitalinnovation.personapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import one.digitalinnovation.personapi.controller.MessageResponseDTO;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.model.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;
import one.digitalinnovation.personapi.request.PersonDto;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class PersonService {
	
	private final  PersonRepository repository;
	
	private final PersonMapper personMapper = PersonMapper.INSTANCE;
	
	
	
	public MessageResponseDTO createPerson(PersonDto personDto){
		
		 Person personToSave = personMapper.toModel(personDto);
		 
		 Person savedPerson = repository.save(personToSave);
	
		return MessageResponseDTO.builder()
				.message("Person Created Sucessfully With ID: " + savedPerson.getId())
				.build();
			
	}

	public List<PersonDto> listAll() {
		List<Person> allPeople = repository.findAll();
		return  allPeople.stream()
				.map(personMapper::toDTO)
				.collect(Collectors.toList());
	}

	public PersonDto findById(Long id) throws PersonNotFoundException  {
		Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		return personMapper.toDTO(person);
	}
	
	public MessageResponseDTO updateById(Long id, PersonDto personDto) throws PersonNotFoundException {
		
		Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		
		Person personToSave = personMapper.toModel(personDto);
		 
		 Person updatedPerson = repository.save(personToSave);
	
		return MessageResponseDTO.builder()
				.message("Updated Person With ID: " + updatedPerson.getId())
				.build();
	}

	public void delete(Long id) throws PersonNotFoundException {
		repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		repository.deleteById(id);
		
	}

	
	
}
