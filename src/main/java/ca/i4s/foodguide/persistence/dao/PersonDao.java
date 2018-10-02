package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.persistence.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Optional;

@Repository
public class PersonDao implements FoodGuideDao<Person, String> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into person (username, family_id, gender, age, date_created, date_updated) " +
            "values (:username, :familyId, :gender, :age, :dateCreated, :dateUpdated)";

    private static final String UPDATE_SQL =
        "update person set family_id = :familyId, gender = :gender, age = :age, date_updated = :dateUpdated " +
            "where username = :username";

    private static final String DELETE_SQL =
        "delete from person where username = :username";

    private static final String SELECT_SQL =
        "select username, family_id as familyId, gender, age, date_created, date_updated " +
            "from person where username = :username";

    private Sql sql;

    @Autowired
    public PersonDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(Person model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            Date now = new Date();
            query
                .addParameter("username", model.getUsername())
                .addParameter("familyId", model.getFamilyId())
                .addParameter("gender", model.getGender().getSymbol())
                .addParameter("age", model.getAge())
                .addParameter("dateCreated", now)
                .addParameter("dateUpdated", now)
                .executeUpdate();
        }
    }

    @Override
    public void update(Person model) {
        LOG.info("Updating: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(UPDATE_SQL)
        ) {
            query
                .addParameter("username", model.getUsername())
                .addParameter("familyId", model.getFamilyId())
                .addParameter("gender", model.getGender().getSymbol())
                .addParameter("age", model.getAge())
                .addParameter("dateUpdated", new Date())
                .executeUpdate();
        }
    }

    @Override
    public void delete(String username) {
        LOG.info("Deleting person: " + username);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(DELETE_SQL)
        ) {
            query
                .addParameter("username", username)
                .executeUpdate();
        }
    }

    @Override
    public Optional<Person> get(String username) {
        LOG.info("Retrieving person: " + username);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(SELECT_SQL)
        ) {
            return Optional.ofNullable(query
                .addParameter("username", username)
                .addColumnMapping("date_created", "dateCreated")
                .addColumnMapping("date_updated", "dateUpdated")
                .executeAndFetchFirst(Person.class));
        }
    }
}