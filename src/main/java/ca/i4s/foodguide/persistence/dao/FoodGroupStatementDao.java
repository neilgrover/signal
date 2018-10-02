package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.FoodGroupStatement;
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
public class FoodGroupStatementDao implements FoodGuideDao<FoodGroupStatement, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into food_group_statement (food_group_id, statement_text) values (:foodGroupId, :statement)";
    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "";
    private static final String SELECT_SQL = "";

    private Sql sql;

    @Autowired
    public FoodGroupStatementDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(FoodGroupStatement model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            query
                .addParameter("foodGroupId", model.getFoodGroupId())
                .addParameter("statement", model.getStatement())
                .executeUpdate();
        }
    }

    @Override
    public void update(FoodGroupStatement model) {
        // TODO implement food-group-statement UPDATE
    }

    @Override
    public void delete(Integer id) {
        // TODO implement food-group-statement DELETE
    }

    @Override
    public Optional<FoodGroupStatement> get(Integer id) {
        // TODO implement food-group-statement GET
        return Optional.empty();
    }
}
