package com.glen.assignment.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationException extends RuntimeException {
    private Object data;
    private String message;
    private String description;
    private int statusCode;

    public ApplicationException(String message, String description, Object data, int statusCode) {
        this.data = data;
        this.message = message;
        this.description = description;
        this.statusCode = statusCode;
    }
}
