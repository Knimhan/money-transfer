package de.revolut.core.exception;

import javax.ws.rs.BadRequestException;

public class BadTransactionRequestException extends BadRequestException {

    public BadTransactionRequestException(String message) {
        super("Money transfer application has failed due to wrong inputs: " + message);
    }
}
