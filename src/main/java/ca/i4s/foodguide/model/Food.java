package ca.i4s.foodguide.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "foodGroupCategoryId", "name", "servingSize"})
public class Food extends FoodGuideModel {

    private Integer id;
    private Integer foodGroupCategoryId;
    private String name;
    private String servingSize;
}
