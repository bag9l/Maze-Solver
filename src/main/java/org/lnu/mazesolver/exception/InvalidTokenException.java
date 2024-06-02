package org.lnu.mazesolver.exception;

public class InvalidTokenException  extends RuntimeException {
    private static final String INVALID_TOKEN = "Invalid token.";

    public InvalidTokenException(String message) {
        super(message.isEmpty() ? INVALID_TOKEN : message);
    }

    public InvalidTokenException() {
        super(INVALID_TOKEN);
    }
}
