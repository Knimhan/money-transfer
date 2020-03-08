package de.revolut.core.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private UUID id;

    private BigDecimal balance;

    private String accountHolder;

    private String currency;

    public Account(UUID id, BigDecimal balance, String accountHolder, String currency) {
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
        this.currency = currency;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getCurrency() {
        return currency;
    }
}
