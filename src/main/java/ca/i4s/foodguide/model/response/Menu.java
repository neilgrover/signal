package ca.i4s.foodguide.model.response;

import ca.i4s.foodguide.model.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter @Builder
public class Menu {

    private String username;
    private Integer age;
    private Gender gender;
    private Collection<FoodGroup> foodGroups = new ArrayList<>();

    public void addFoodGroup(FoodGroup foodGroup) {
        foodGroups.add(foodGroup);
    }

    @Getter @Setter @Builder
    public static class FoodGroup {

        private String name;
        private String directionalStatement;
        private Integer minServing;
        private Integer maxServing;
        private Collection<FoodGroupCategory> foodGroupCategories = new ArrayList<>();

        public void addFoodGroupCategory(FoodGroupCategory foodGroupCategory) {
            foodGroupCategories.add(foodGroupCategory);
        }

        @Getter @Setter @Builder
        public static class FoodGroupCategory {

            private String description;
            private Collection<MenuItem> menuItems = new ArrayList<>();

            public void addMenuItem(MenuItem menuItem) {
                menuItems.add(menuItem);
            }

            @Getter @Setter @Builder
            public static class MenuItem {

                private String name;
                private String servingSize;
            }

        }
    }
}
