package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.Food;
import ca.i4s.foodguide.persistence.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FoodDao implements FoodGuideDao<Food, Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String INSERT_SQL =
        "insert into food (food_group_category_id, name, serving_size) " +
            "values (:foodGroupCategoryId, :name, :servingSize)";
    private static final String SELECT_BY_FOOD_GROUP_CATEGORY_ID_SQL =
        "select id, food_group_category_id, name, serving_size, date_created, date_updated " +
            "from food " +
            "where food_group_category_id = :foodGroupCategoryId";

    private Sql sql;

    @Autowired
    public FoodDao(Sql sql) {
        this.sql = sql;
    }

    @Override
    public void save(Food model) {
        LOG.info("Saving: " + model);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(INSERT_SQL)
        ) {
            query
                .addParameter("foodGroupCategoryId", model.getFoodGroupCategoryId())
                .addParameter("name", model.getName())
                .addParameter("servingSize", model.getServingSize())
                .executeUpdate();
        }
    }

    public Collection<Food> getForFoodGroupCategoryId(Integer foodGroupCategoryId) {
        LOG.info("Retrieving foods for food group category id: " + foodGroupCategoryId);
        try (Connection conn = sql.open();
             Query query = conn.createQuery(SELECT_BY_FOOD_GROUP_CATEGORY_ID_SQL)
        ) {
            return query
                .addParameter("foodGroupCategoryId", foodGroupCategoryId)
                .addColumnMapping("date_created", "dateCreated")
                .addColumnMapping("date_updated", "dateUpdated")
                .addColumnMapping("food_group_category_id", "foodGroupCategoryId")
                .addColumnMapping("serving_size", "servingSize")
                .executeAndFetch(Food.class);
        }
    }

    @Override
    public void update(Food model) {
        // TODO implement food UPDATE
    }

    @Override
    public void delete(Integer id) {
        // TODO implement food DELETE
    }

    @Override
    public Optional<Food> get(Integer id) {
        // TODO implement food GET
        return Optional.empty();
    }
}
