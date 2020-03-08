package de.revolut.core.exception;

import javax.ws.rs.BadRequestException;

public class InsufficientBalanceException extends BadRequestException {

    public InsufficientBalanceException(String message) {
        super("Money transfer application has failed due to: " + message);
    }
}
