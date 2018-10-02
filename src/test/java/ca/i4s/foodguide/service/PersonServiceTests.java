package ca.i4s.foodguide.service;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.persistence.Sql;
import ca.i4s.foodguide.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTests {

    private Person person;

    @Autowired
    private PersonService personService;

    @Before
    public void setup() {
        person = Person.builder()
            .age(12)
            .username("agrover")
            .gender(Gender.FEMALE)
            .build();
    }

    @Test
    public void createAndRetrievePerson() {
        personService.save(person);

        assertNull(person.getDateCreated());

        Person savedPerson = personService.get(person.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(Person.class));

        assertNotNull(savedPerson.getDateCreated());
    }

}
