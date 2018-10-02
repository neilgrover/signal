package ca.i4s.foodguide.service;

import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.model.response.Menu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * I am light on tests here I realize. Ideally I'd have some test data files in the src/test/resources folder
 * that I could use for loading menus and validating data more precisely. I'm outta time
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuServiceTests {

    private Person person;

    @Autowired
    private MenuService menuService;

    @Before
    public void setup() {
        person = Person.builder()
            .age(12)
            .username("agrover")
            .gender(Gender.FEMALE)
            .build();
    }

    @Test
    public void testPersonMenu() {
        Menu menu = menuService.getPersonMenu(person);

        assertNotNull(menu);
        assertEquals(menu.getUsername(), person.getUsername());
        assertNotNull(menu.getFoodGroups());
        assertTrue(menu.getFoodGroups().size() > 0);
    }
}
