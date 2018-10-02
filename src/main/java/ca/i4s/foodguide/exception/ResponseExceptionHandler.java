package ca.i4s.foodguide.exception;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This is a last line of defence for sending messages to the user when an exception occurs that isn't
 * handled in the code. Currently we are only handling sql related exceptions as we don't want to send
 * verbose and sensitive sql statements to the user.
 */
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {JdbcSQLException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, String.format("An internal error occurred: %s", ex.getClass().getSimpleName()),
            new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
