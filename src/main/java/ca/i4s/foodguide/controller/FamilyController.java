package ca.i4s.foodguide.controller;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.Family;
import ca.i4s.foodguide.model.request.FamilyRequest;
import ca.i4s.foodguide.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamilyController {

    private FamilyService familyService;

    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/family/{username}")
    public Family get(@PathVariable(value = "username") String username) {
        return familyService
            .get(username)
            .orElseThrow(() ->
                new EntityNotFoundException(Family.class));
    }

    /**
     * save a new family by providing an array of usernames. CAUTION this will overwrite
     * existing families if usernames are already assigned. This could obviously be improved.
     * @param familyRequest includes an array of "usernames" for which a family is to be created.
     * @return a family represented by the usersnames provided
     */
    @PostMapping("/family")
    public Family save(@RequestBody FamilyRequest familyRequest) {
        return familyService.save(familyRequest.getUsernames());
    }
}
