package ca.i4s.foodguide.controller;

import ca.i4s.foodguide.FoodGuideApplication;
import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.model.request.PersonRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoodGuideApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerTests {

    private PersonRequest personRequest;
    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        personRequest = PersonRequest.builder()
            .username("agrover")
            .age(12)
            .gender("F")
            .build();
    }

    @Test
    public void savePerson() throws Exception {
        HttpEntity<PersonRequest> entity = new HttpEntity<>(personRequest, headers);

        ResponseEntity<Person> response = restTemplate.exchange(
            createURLWithPort("/person"),
            HttpMethod.POST,
            entity,
            Person.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        Person person = response.getBody();
        assertNotNull(person);

        assertEquals(person.getUsername(), personRequest.getUsername());
        assertEquals(person.getGender().getSymbol(), personRequest.getGender());
        assertEquals(person.getAge(), person.getAge());
    }

    private String createURLWithPort(String uri) {
        return String.format("http://localhost:%s%s", port, uri);
    }
}
