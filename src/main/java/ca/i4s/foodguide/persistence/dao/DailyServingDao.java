package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.DailyServing;
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
public class DailyServingDao implements FoodGuideDao<DailyServing, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into daily_serving (food_group_id, gender, min_age, max_age, min_serving, max_serving) " +
            "values (:foodGroupId, :gender, :minAge, :maxAge, :minServing, :maxServing)";
    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "";
    private static final String SELECT_SQL = "";

    private Sql sql;

    @Autowired
    public DailyServingDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(DailyServing model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            query
                .addParameter("foodGroupId", model.getFoodGroupId())
                .addParameter("gender", model.getGender().getSymbol())
                .addParameter("minAge", model.getMinAge())
                .addParameter("maxAge", model.getMaxAge())
                .addParameter("minServing", model.getMinServing())
                .addParameter("maxServing", model.getMaxServing())
                .executeUpdate();
        }
    }

    @Override
    public void update(DailyServing model) {
        // TODO implement daily-serving UPDATE
    }

    @Override
    public void delete(Integer id) {
        // TODO implement daily-serving DELETE
    }

    @Override
    public Optional<DailyServing> get(Integer id) {
        // TODO implement daily-serving GET
        return Optional.empty();
    }
}
