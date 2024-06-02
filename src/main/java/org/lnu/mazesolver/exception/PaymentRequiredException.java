package org.lnu.mazesolver.exception;

public class PaymentRequiredException extends RuntimeException {
    private static final String PAYMENT_REQUIRED = "Need some payment to access.";

    public PaymentRequiredException(String message) {
        super(message.isEmpty() ? PAYMENT_REQUIRED : message);
    }

    public PaymentRequiredException() {
        super(PAYMENT_REQUIRED);
    }
}
