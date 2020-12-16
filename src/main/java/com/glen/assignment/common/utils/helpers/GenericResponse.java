package com.glen.assignment.common.utils.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericResponse<S, E extends Boolean> {
    S body;
    E error;
    String shortCode;
    String message;
    String description;
}
