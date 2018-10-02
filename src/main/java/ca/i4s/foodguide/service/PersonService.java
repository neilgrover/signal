package ca.i4s.foodguide.service;

import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.persistence.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PersonService {

    private PersonDao personDao;

    @Autowired
    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void save(Person person) {
        personDao.save(person);
    }

    public void update(Person person) {
        personDao.update(person);
    }

    public void delete(String username) {
        personDao.delete(username);
    }

    public Optional<Person> get(String username) {
        return personDao.get(username);
    }

    public Collection<Person> getForFamilyId(Integer familyId) {
        return personDao.getForFamilyId(familyId);
    }
}
