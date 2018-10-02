package ca.i4s.foodguide.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {

    private String username;
    private Integer familyId;
    private String gender;
    private Integer age;
}
