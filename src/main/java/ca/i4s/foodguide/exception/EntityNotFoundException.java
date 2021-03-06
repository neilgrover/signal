package ca.i4s.foodguide.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entityClass) {
        super(String.format("%s not found", entityClass.getSimpleName()));
    }
}
