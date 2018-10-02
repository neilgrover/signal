package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.Family;
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
public class FamilyDao implements FoodGuideDao<Family, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String SELECT_SQL =
        "select id, date_created, date_updated " +
            "from family " +
            "where id = :id";
    private static final String INSERT_SQL =
        "insert into family (date_created, date_updated) " +
            "values (:dateCreated, :dateUpdated)";

    private Sql sql;

    @Autowired
    public FamilyDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(Family model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            Date now = new Date();
            Integer id = query
                .addParameter("dateCreated", now)
                .addParameter("dateUpdated", now)
                .executeUpdate()
                .getKey(Integer.class);
            model.setId(id);
        }
    }

    @Override
    public Optional<Family> get(Integer id) {
        LOG.info("Retrieving family: " + id);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(SELECT_SQL)
        ) {
            return Optional.ofNullable(query
                .addParameter("id", id)
                .addColumnMapping("date_created", "dateCreated")
                .addColumnMapping("date_updated", "dateUpdated")
                .executeAndFetchFirst(Family.class));
        }
    }

    @Override
    public void update(Family model) {
        // TODO
    }

    @Override
    public void delete(Integer id) {
        // TODO
    }
}
