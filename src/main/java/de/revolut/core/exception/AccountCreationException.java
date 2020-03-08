package de.revolut.core.exception;

public class AccountCreationException extends RuntimeException {

    public AccountCreationException(String message) {
        super("Money transfer application failed to create account: " + message);
    }
}
