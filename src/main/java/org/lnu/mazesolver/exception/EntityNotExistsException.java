package org.lnu.mazesolver.exception;

public class EntityNotExistsException extends RuntimeException {
    private static final String ENTITY_NOT_EXISTS = "Unable to find entity.";

    public EntityNotExistsException(String message) {
        super(message.isEmpty() ? ENTITY_NOT_EXISTS : message);
    }

    public EntityNotExistsException() {
        super(ENTITY_NOT_EXISTS);
    }
}
