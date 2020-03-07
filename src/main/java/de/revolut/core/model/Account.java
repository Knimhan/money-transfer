package de.revolut.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class Account {

    private UUID id;

    private BigDecimal balance;

    public Account() {
    }

    public Account(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    @JsonProperty
    public UUID getId() {
        return id;
    }

    @JsonProperty
    public BigDecimal getBalance() {
        return balance;
    }
}
