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
@EqualsAndHashCode(of = {"username", "familyId", "gender", "age"})
public class Person extends FoodGuideModel {

    private String username;
    private Integer familyId;
    private Gender gender;
    private Integer age;
}
