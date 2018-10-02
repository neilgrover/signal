package ca.i4s.foodguide.service;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.Family;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.persistence.dao.FamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FamilyService {

    private FamilyDao familyDao;
    private PersonService personService;

    @Autowired
    public FamilyService(FamilyDao familyDao, PersonService personService) {
        this.familyDao = familyDao;
        this.personService = personService;
    }

    public Optional<Family> get(String username) {
        Person person = personService.get(username)
            .orElseThrow(() -> new EntityNotFoundException(Person.class));
        if (null == person.getFamilyId()) {
            return Optional.empty();
        } else {
            Collection<Person> people = personService.getForFamilyId(person.getFamilyId());
            Optional<Family> family = familyDao.get(person.getFamilyId());
            family.ifPresent(f -> f.setPeople(people));
            return family;
        }
    }

    public Family save(Collection<String> usernames) {
        // guard cases
        if (CollectionUtils.isEmpty(usernames)) {
            // TODO externalize messages ( i18n )
            throw new RuntimeException("Family must have at least one member");
        }

        Date now = new Date();
        Family family = new Family();
        family.setDateCreated(now);
        family.setDateUpdated(now);

        List<Person> people = usernames.stream()
            .map(personService::get)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        family.setPeople(people);
        familyDao.save(family);

        people.forEach(person -> {
            person.setFamilyId(family.getId());
            personService.update(person);
        });

        return family;
    }
}
