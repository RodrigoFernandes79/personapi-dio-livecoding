package one.digitalinnovation.personapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.request.PersonDto;
import one.digitalinnovation.personapi.service.PersonService;
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController 
@RequestMapping("/people") // localhost:8095/people
public class PersonController {
	
	private PersonService personService;
		
	
		
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
		return personService.findById(id);
		
	}
	@PutMapping("/{id}")
	public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid PersonDto personDto) throws PersonNotFoundException {
		return personService.updateById(id, personDto);
		
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public   void deleteById(@PathVariable Long id) throws PersonNotFoundException {
		 personService.delete(id);
	}
}
