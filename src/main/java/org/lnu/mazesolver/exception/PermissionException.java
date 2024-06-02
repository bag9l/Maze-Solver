package org.lnu.mazesolver.exception;

public class PermissionException extends RuntimeException {
    private static final String PERMISSION_DENIED = "Permission denied.";

    public PermissionException(String message) {
        super(message.isEmpty() ? PERMISSION_DENIED : message);
    }

    public PermissionException() {
        super(PERMISSION_DENIED);
    }
}
