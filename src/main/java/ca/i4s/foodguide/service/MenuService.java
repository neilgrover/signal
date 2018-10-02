package ca.i4s.foodguide.service;

import ca.i4s.foodguide.exception.EntityNotFoundException;
import ca.i4s.foodguide.model.DailyServing;
import ca.i4s.foodguide.model.Family;
import ca.i4s.foodguide.model.Food;
import ca.i4s.foodguide.model.FoodGroup;
import ca.i4s.foodguide.model.FoodGroupCategory;
import ca.i4s.foodguide.model.FoodGroupStatement;
import ca.i4s.foodguide.model.Gender;
import ca.i4s.foodguide.model.Person;
import ca.i4s.foodguide.model.response.Menu;
import ca.i4s.foodguide.persistence.dao.DailyServingDao;
import ca.i4s.foodguide.persistence.dao.FoodDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupCategoryDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupDao;
import ca.i4s.foodguide.persistence.dao.FoodGroupStatementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    // TODO: these should all be services instead of DAO's but I'm out of time to write the boiler code.
    private FamilyService familyService;
    private DailyServingDao dailyServingDao;
    private FoodGroupDao foodGroupDao;
    private FoodGroupStatementDao foodGroupStatementDao;
    private FoodGroupCategoryDao foodGroupCategoryDao;
    private FoodDao foodDao;

    @Autowired
    public MenuService(DailyServingDao dailyServingDao,
                       FoodGroupDao foodGroupDao,
                       FoodGroupStatementDao foodGroupStatementDao,
                       FoodGroupCategoryDao foodGroupCategoryDao,
                       FoodDao foodDao,
                       FamilyService familyService) {
        this.dailyServingDao = dailyServingDao;
        this.foodGroupDao = foodGroupDao;
        this.foodGroupStatementDao = foodGroupStatementDao;
        this.foodGroupCategoryDao = foodGroupCategoryDao;
        this.foodDao = foodDao;
        this.familyService = familyService;
    }

    /**
     * Calculates a daily menu for a given person. Takes into account age and gender.
     *
     * @param person
     * @return a menu that picks random foods from each food group according to recommended serving count
     */
    public Menu getPersonMenu(Person person) {
        Integer age = person.getAge();
        Gender gender = person.getGender();
        Menu menu = Menu.builder()
            .username(person.getUsername())
            .gender(gender)
            .age(age)
            .build();

        Collection<DailyServing> dailyServings = dailyServingDao.getForAgeAndGender(age, gender);

        dailyServings.forEach(dailyServing -> {
            FoodGroup foodGroup = foodGroupDao.get(dailyServing.getFoodGroupId())
                .orElseThrow(() -> new EntityNotFoundException(FoodGroup.class));

            FoodGroupStatement foodGroupStatement = foodGroupStatementDao.getByFoodGroupId(foodGroup.getId())
                .orElseThrow(() -> new EntityNotFoundException(FoodGroupStatement.class));

            Menu.FoodGroup menuFoodGroup = Menu.FoodGroup.builder()
                .name(foodGroup.getName())
                .directionalStatement(foodGroupStatement.getStatementText())
                .minServing(dailyServing.getMinServing())
                .maxServing(dailyServing.getMaxServing())
                .build();

            menu.addFoodGroup(menuFoodGroup);

            Collection<FoodGroupCategory> foodGroupCategories = foodGroupCategoryDao.getByFoodGroupId(foodGroup.getId());
            Integer servigsPerFoodGroupCategory = (int) Math.ceil((dailyServing.getMinServing() + 0.5) / foodGroupCategories.size());

            foodGroupCategories.forEach(foodGroupCategory -> {
                Menu.FoodGroup.FoodGroupCategory menuFoodGroupCategory = Menu.FoodGroup.FoodGroupCategory.builder()
                    .description(foodGroupCategory.getName())
                    .build();
                menuFoodGroup.addFoodGroupCategory(menuFoodGroupCategory);

                Collection<Food> foods = foodDao.getForFoodGroupCategoryId(foodGroupCategory.getId());

                // lets pick a random sampling of foods to a max of the number of min servings
                Collection<Food> menuFoods = getRandomSamplingOfFood(foods, servigsPerFoodGroupCategory);
                menuFoods.forEach(food -> {
                    Menu.FoodGroup.FoodGroupCategory.MenuItem menuItem = Menu.FoodGroup.FoodGroupCategory.MenuItem.builder()
                        .name(food.getName())
                        .servingSize(food.getServingSize())
                        .build();

                    menuFoodGroupCategory.addMenuItem(menuItem);
                });
            });
        });

        return menu;
    }

    public List<Menu> getFamilyMenu(Person person) {
        Family family = familyService.get(person.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(Family.class));

        List<Menu> familyMenus = family.getPeople().stream()
            .map(this::getPersonMenu)
            .collect(Collectors.toList());

        return familyMenus;
    }

    /**
     * Picks a random number of foods from a list of foods. This will cause the API to give different food suggestions for every call.
     *
     * @param allFoods list of all foods to pick random foods from
     * @param servings the max number of foods(servings) to pick randomly
     * @return a random selection of foods from a list of foods
     */
    private Collection<Food> getRandomSamplingOfFood(Collection<Food> allFoods, int servings) {
        ArrayList<Food> randomizedFoods = new ArrayList<>(allFoods.size());
        randomizedFoods.addAll(allFoods);

        // shuffle food
        Collections.shuffle(randomizedFoods);

        // adding defined amount of food to list
        ArrayList<Food> randomFoods = new ArrayList<>();
        for (int i = 0; i < servings && i < randomizedFoods.size(); i++) {
            randomFoods.add(randomizedFoods.get(i));
        }

        return randomFoods;
    }
}
