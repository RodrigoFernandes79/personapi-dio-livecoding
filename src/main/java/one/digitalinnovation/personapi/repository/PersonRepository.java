package one.digitalinnovation.personapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import one.digitalinnovation.personapi.model.Person;


interface PersonRepository extends JpaRepository <Person, Long> {
	 

	
}
