package one.digitalinnovation.personapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.request.PersonDto;
import one.digitalinnovation.personapi.service.PersonService;

@RestController 
@RequestMapping("/people") // localhost:8095/people
public class PersonController {
	
	private PersonService personService;
		
	@Autowired
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
		
	@PostMapping 
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponseDTO createPerson(@RequestBody @Valid PersonDto personDto){
		return personService.createPerson(personDto);
		
	}
	@GetMapping
	public List<PersonDto> listAll(){		
		return personService.listAll();
		
	}
	
	@GetMapping("/{id}")
	public PersonDto findById(@PathVariable Long id) throws PersonNotFoundException {
		return personService.findById(id) ;
		
	}
}
