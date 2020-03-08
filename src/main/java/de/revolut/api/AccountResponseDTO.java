package de.revolut.api;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountResponseDTO {
    private UUID id;

    private BigDecimal balance;

    private String accountHolder;

    private String currency;

    public AccountResponseDTO() {
    }

    public AccountResponseDTO(UUID id, BigDecimal balance, String accountHolder, String currency) {
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

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
