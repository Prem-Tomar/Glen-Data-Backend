package com.glen.assignment.common.exceptions;

public class NoResultsException extends ApplicationException{
    public NoResultsException(String message, String description, Object data, int statusCode) {
        super(message, description, data, statusCode);
    }
}
