package com.honeywell.atm.core.model.exception;

public enum ExceptionMessage {
    INVALID_CARD_NUMBER("The card introduced is not valid!"),
    INCORRECT_PIN("The PIN introduced is not correct!"),
    INSUFFICIENT_FUNDS("Amount not available for withdrawal!"),
    INVALID_AMOUNT("The amount should be a positive number!"),
    INACTIVE_CARD("The card introduced is inactive!"),
    NOT_AUTHORIZED("Operation not authorized!"),
    ALREADY_LOGGED("User already logged in another terminal!");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
