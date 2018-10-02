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
@EqualsAndHashCode(of = {"id", "foodGroupId", "gender", "minAge", "maxAge", "minServing", "maxServing"})
public class DailyServing extends FoodGuideModel {

    private Integer id;
    private String foodGroupId;
    private Gender gender;
    private Integer minAge;
    private Integer maxAge;
    private Integer minServing;
    private Integer maxServing;
}
