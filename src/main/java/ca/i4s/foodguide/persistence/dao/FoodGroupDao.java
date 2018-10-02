package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.FoodGroup;
import ca.i4s.foodguide.persistence.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

@Repository
public class FoodGroupDao implements FoodGuideDao<FoodGroup, String> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into food_group (id, name) " +
            "values (:id, :name)";
    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "";
    private static final String SELECT_SQL = "";

    private Sql sql;

    @Autowired
    public FoodGroupDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(FoodGroup model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            query
                .addParameter("id", model.getId())
                .addParameter("name", model.getName())
                .executeUpdate();
        }
    }

    @Override
    public void update(FoodGroup model) {
        // TODO implement food-group UPDATE
    }

    @Override
    public void delete(String id) {
        // TODO implement food-group DELETE
    }

    @Override
    public Optional<FoodGroup> get(String id) {
        // TODO implement food-group GET
        return Optional.empty();
    }
}
