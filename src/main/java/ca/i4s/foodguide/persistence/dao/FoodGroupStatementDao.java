package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.DailyServing;
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
        "insert into food_group_statement (food_group_id, statement_text) values (:foodGroupId, :statementText)";
    private static final String SELECT_BY_FOOD_GROUP_ID =
        "select id, date_created, date_updated, food_group_id, statement_text " +
            "from food_group_statement " +
            "where food_group_id = :foodGroupId";
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
                .addParameter("statementText", model.getStatementText())
                .executeUpdate();
        }
    }

    public Optional<FoodGroupStatement> getByFoodGroupId(String foodGroupId) {
        LOG.info("Retrieving food group statement for food group id: " + foodGroupId);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(SELECT_BY_FOOD_GROUP_ID)
        ) {
            return Optional.ofNullable(query
                .addParameter("foodGroupId", foodGroupId)
                .addColumnMapping("food_group_id", "foodGroupId")
                .addColumnMapping("statement_text", "statementText")
                .addColumnMapping("date_created", "dateCreated")
                .addColumnMapping("date_updated", "dateUpdated")
                .executeAndFetchFirst(FoodGroupStatement.class));
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
