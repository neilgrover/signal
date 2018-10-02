package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.FoodGroupCategory;
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
public class FoodGroupCategoryDao implements FoodGuideDao<FoodGroupCategory, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into food_group_category (id, name, food_group_id) " +
            "values (:id, :name, :foodGroupId)";
    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "";
    private static final String SELECT_SQL = "";

    private Sql sql;

    @Autowired
    public FoodGroupCategoryDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(FoodGroupCategory model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            query
                .addParameter("id", model.getId())
                .addParameter("name", model.getName())
                .addParameter("foodGroupId", model.getFoodGroupId())
                .executeUpdate();
        }
    }

    @Override
    public void update(FoodGroupCategory model) {
        // TODO implement food-group-category UPDATE
    }

    @Override
    public void delete(Integer id) {
        // TODO implement food-group-category DELETE
    }

    @Override
    public Optional<FoodGroupCategory> get(Integer id) {
        // TODO implement food-group-category GET
        return Optional.empty();
    }
}
