package org.lnu.mazesolver.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError {
    private List<Object> object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(List<Object> object, String message) {
        this.object = object;
        this.message = message;
    }
}
