package ca.i4s.foodguide.controller;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.model.request.PersonRequest;
import ca.i4s.foodguide.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{username}")
    public Person get(@PathVariable(value = "username") String username) {
        return personService
            .get(username)
            .orElseThrow(() ->
                new EntityNotFoundException(Person.class));
    }

    @PostMapping("/person")
    public Person save(@RequestBody PersonRequest personRequest) {
        Person person = Person.builder()
            .username(personRequest.getUsername())
            .age(personRequest.getAge())
            .gender(Gender.valueOfSymbol(personRequest.getGender()))
            .familyId(personRequest.getFamilyId())
            .build();

        personService.save(person);
        return person;
    }
}