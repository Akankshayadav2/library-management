package com.akanksha.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when something we look up (book, member, issue record) does not exist.
 *
 * @ResponseStatus(NOT_FOUND) tells Spring: if this exception escapes a controller,
 * answer with HTTP 404. (In Phase 2 our GlobalExceptionHandler takes over and
 * returns a cleaner JSON body.)
 *
 * It extends RuntimeException (unchecked), so we don't need "throws" everywhere.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
