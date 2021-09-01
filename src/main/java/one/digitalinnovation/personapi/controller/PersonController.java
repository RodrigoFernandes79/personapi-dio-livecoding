package one.digitalinnovation.personapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.model.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;

@RestController 
@RequestMapping("/people") // localhost:8095/people
public class PersonController {
	
	private final PersonRepository repository; 
	
	@Autowired 
	public PersonController(PersonRepository repository) { 
		this.repository = repository;
		
	}
	@PostMapping 
	public MessageResponseDTO createPerson(@RequestBody Person person){
							
		 Person savedPerson = repository.save(person);
		return MessageResponseDTO.builder()
				.message("Person Created Sucessfully With ID: " + savedPerson.getId())
				.build();
		
		
		
	}

}
