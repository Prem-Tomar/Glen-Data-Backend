package com.glen.assignment.common.exceptions;

import com.glen.assignment.common.utils.Constants;
import com.glen.assignment.common.utils.helpers.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(NoResultsException.class)
    ResponseEntity<GenericResponse<Object, Boolean>> handleNoResultsException(NoResultsException exception) {
        return ResponseEntity.status(exception.getStatusCode()).body(new GenericResponse<>(exception.getData(), true, Constants.FAILED, exception.getMessage(), exception.getDescription()));
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity handleAllExceptions(RuntimeException exception) {
        return ResponseEntity.status(500).body(new GenericResponse<>(exception, true, Constants.FAILED, exception.getMessage(), exception.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity handleAllExceptions(Exception exception) {
        return ResponseEntity.status(500).body(new GenericResponse<>(exception, true, Constants.FAILED, exception.getMessage(), exception.getLocalizedMessage()));
    }
}
