package de.revolut.core.exception;

import javax.ws.rs.NotFoundException;

public class AccountNotFoundException extends NotFoundException {

    public AccountNotFoundException(String message) {
        super("Money transfer application can not find account: " + message);
    }
}
