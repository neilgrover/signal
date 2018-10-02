package ca.i4s.foodguide.service;

import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Menu;
import ca.i4s.foodguide.model.Person;
import org.springframework.stereotype.Component;

@Component
public class MenuService {

    public Menu getMenu(Person person) {
        Integer age = person.getAge();
        Gender gender = person.getGender();
        return new Menu();
    }
}
