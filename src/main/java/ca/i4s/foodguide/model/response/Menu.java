package ca.i4s.foodguide.model.response;

import ca.i4s.foodguide.model.Gender;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Builder
public class Menu {

    private String username;
    private Integer age;
    private Gender gender;
    private Collection<FoodGroup> foodGroups;

    public void addFoodGroup(FoodGroup foodGroup) {
        foodGroups = MoreObjects.firstNonNull(foodGroups, Lists.newArrayList());
        foodGroups.add(foodGroup);
    }

    @Getter
    @Setter
    @Builder
    public static class FoodGroup {

        private String name;
        private String directionalStatement;
        private Integer minServing;
        private Integer maxServing;
        private Collection<FoodGroupCategory> foodGroupCategories;

        public void addFoodGroupCategory(FoodGroupCategory foodGroupCategory) {
            foodGroupCategories = MoreObjects.firstNonNull(foodGroupCategories, new ArrayList<>());
            foodGroupCategories.add(foodGroupCategory);
        }

        @Getter
        @Setter
        @Builder
        public static class FoodGroupCategory {

            private String description;
            private Collection<MenuItem> menuItems;

            public void addMenuItem(MenuItem menuItem) {
                menuItems = MoreObjects.firstNonNull(menuItems, new ArrayList<>());
                menuItems.add(menuItem);
            }

            @Getter
            @Setter
            @Builder
            public static class MenuItem {

                private String name;
                private String servingSize;
            }
        }
    }
}
