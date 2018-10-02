package ca.i4s.foodguide.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequest {

    private String username;
    private Integer familyId;
    private String gender;
    private Integer age;
}
