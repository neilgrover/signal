package ca.i4s.foodguide.controller;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.response.Menu;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.service.MenuService;
import ca.i4s.foodguide.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FoodGuideController {

    private PersonService personService;
    private MenuService menuService;

    @Autowired
    public FoodGuideController(PersonService personService, MenuService menuService) {
        this.personService = personService;
        this.menuService = menuService;
    }

    @GetMapping("/menu/{username}")
    public Menu getUserMenu(@PathVariable(value = "username") String username) {
        Person person = personService
            .get(username)
            .orElseThrow(() ->
                new EntityNotFoundException(Person.class));
        return menuService.getPersonMenu(person);
    }

    @GetMapping("/family-menu/{username}")
    public List<Menu> getFamilyMenu(@PathVariable(value = "username") String username) {
        Person person = personService
            .get(username)
            .orElseThrow(() ->
                new EntityNotFoundException(Person.class));
        return menuService.getFamilyMenu(person);
    }
}
