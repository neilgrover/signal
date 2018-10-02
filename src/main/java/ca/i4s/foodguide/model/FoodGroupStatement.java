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
@EqualsAndHashCode(of = {"id", "foodGroupId", "statement"})
public class FoodGroupStatement extends FoodGuideModel {

    private Integer id;
    private String foodGroupId;
    private String statement;
}