package ca.i4s.foodguide.persistence;

import ca.i4s.foodguide.model.DailyServing;
import ca.i4s.foodguide.model.Food;
import ca.i4s.foodguide.model.FoodGroup;
import ca.i4s.foodguide.model.FoodGroupCategory;
import ca.i4s.foodguide.model.FoodGroupStatement;
import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.persistence.dao.DailyServingDao;
import ca.i4s.foodguide.persistence.dao.FoodDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupCategoryDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupStatementDao;
import com.google.common.base.Charsets;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DbInitializingBean implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FOOD_GROUP_CSV_PATH = "/data/foodgroups-en_ONPP.csv";
    private static final String FOOD_CSV_PATH = "/data/foods-en_ONPP_rev.csv";
    private static final String FOOD_STATEMENTS_CSV_PATH = "/data/fg_directional_satements-en_ONPP.csv";
    private static final String FOOD_SERVINGS_CSV_PATH = "/data/servings_per_day-en_ONPP.csv";
    private static final Pattern UNSIGNED_NUMBER_PATTERN = Pattern.compile("[0-9]+");

    private FoodGroupDao foodGroupDao;
    private FoodGroupCategoryDao foodGroupCategoryDao;
    private FoodGroupStatementDao foodGroupStatementDao;
    private FoodDao foodDao;
    private DailyServingDao dailyServingDao;

    @Autowired
    public DbInitializingBean(FoodGroupDao foodGroupDao,
                              FoodGroupCategoryDao foodGroupCategoryDao,
                              FoodGroupStatementDao foodGroupStatementDao,
                              FoodDao foodDao,
                              DailyServingDao dailyServingDao) {
        this.foodGroupDao = foodGroupDao;
        this.foodGroupCategoryDao = foodGroupCategoryDao;
        this.foodGroupStatementDao = foodGroupStatementDao;
        this.foodDao = foodDao;
        this.dailyServingDao = dailyServingDao;
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        readFoodGroupsAndCategories(
            foodGroupDao::save,
            foodGroupCategoryDao::save);

        readFoods(
            foodDao::save);

        readFoodGroupStatements(
            foodGroupStatementDao::save);

        readDailyFoodServings(
            dailyServingDao::save);
    }

    private void readFoodGroupsAndCategories(Consumer<FoodGroup> foodGroupConsumer, Consumer<FoodGroupCategory> foodGroupCategoryConsumer) throws IOException {
        Collection<FoodGroup> foodGroups = new HashSet<>();
        Collection<FoodGroupCategory> foodGroupCategories = new HashSet<>();

        readRows(FOOD_GROUP_CSV_PATH, row -> {
            String foodGroupId = row[0];
            String foodGroupName = row[1];
            Integer foodGroupCategoryId = Integer.parseInt(row[2]);
            String foodGroupCategoryName = row[3];

            foodGroups.add(FoodGroup.builder()
                .id(foodGroupId)
                .name(foodGroupName)
                .build());

            foodGroupCategories.add(FoodGroupCategory.builder()
                .id(foodGroupCategoryId)
                .name(foodGroupCategoryName)
                .foodGroupId(foodGroupId)
                .build());
        });

        foodGroups.forEach(foodGroupConsumer);
        foodGroupCategories.forEach(foodGroupCategoryConsumer);
    }

    private void readFoods(Consumer<Food> foodConsumer) throws IOException {
        readRows(FOOD_CSV_PATH, row -> {
            Integer foodGroupCategoryId = Integer.parseInt(row[1]);
            String servingSize = row[2];
            String name = row[3];

            Food food = Food.builder()
                .foodGroupCategoryId(foodGroupCategoryId)
                .servingSize(servingSize)
                .name(name)
                .build();

            foodConsumer.accept(food);
        });
    }

    private void readFoodGroupStatements(Consumer<FoodGroupStatement> foodGroupStatementConsumer) throws IOException {
        readRows(FOOD_STATEMENTS_CSV_PATH, row -> {
            String foodGroupId = row[0];
            String statementText = row[1];

            FoodGroupStatement foodGroupStatement = FoodGroupStatement.builder()
                .foodGroupId(foodGroupId)
                .statementText(statementText)
                .build();

            foodGroupStatementConsumer.accept(foodGroupStatement);
        });
    }

    private void readDailyFoodServings(Consumer<DailyServing> dailyServingConsumer) throws IOException {
        readRows(FOOD_SERVINGS_CSV_PATH, row -> {
            String foodGroupId = row[0];
            String gender = row[1];
            String ages = row[2];
            String servings = row[3];

            DailyServing dailyServing = DailyServing.builder()
                .foodGroupId(foodGroupId)
                .gender(Gender.valueOfSymbol(gender))
                .build();

            Matcher ageMatcher = UNSIGNED_NUMBER_PATTERN.matcher(ages);
            while (ageMatcher.find() && null == dailyServing.getMaxAge()) {
                int age = Integer.parseInt(ageMatcher.group());
                if (null == dailyServing.getMinAge()) {
                    dailyServing.setMinAge(age);
                } else if (null == dailyServing.getMaxAge()) {
                    dailyServing.setMaxAge(age);
                }
            }

            Matcher servingMatcher = UNSIGNED_NUMBER_PATTERN.matcher(servings);
            while (servingMatcher.find() && null == dailyServing.getMaxServing()) {
                int serving = Integer.parseInt(servingMatcher.group());
                if (null == dailyServing.getMinServing()) {
                    dailyServing.setMinServing(serving);
                } else if (null == dailyServing.getMaxServing()) {
                    dailyServing.setMaxServing(serving);
                }
            }

            dailyServingConsumer.accept(dailyServing);
        });
    }

    private void readRows(String fileName, Consumer<String[]> recordConsumer) throws IOException {
        try (
            Reader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(fileName), Charsets.ISO_8859_1));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] row;
            while ((row = csvReader.readNext()) != null) {
                LOG.info("Reading data row: {}", row);
                recordConsumer.accept(row);
            }
        }
    }
}