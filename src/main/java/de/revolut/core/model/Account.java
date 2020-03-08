package de.revolut.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private UUID id;

    private BigDecimal balance;

    private String accountHolder;

    private String currency;

    public Account() {
    }

    public Account(UUID id, BigDecimal balance, String accountHolder, String currency) {
        this.id = id;
        this.balance = balance;
        this.accountHolder = accountHolder;
        this.currency = currency;
    }

    @JsonProperty //TODO:remove
    public UUID getId() {
        return id;
    }

    @JsonProperty
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
