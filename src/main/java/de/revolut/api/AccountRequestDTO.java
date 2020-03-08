package de.revolut.api;

import javax.validation.constraints.Max;
import java.math.BigDecimal;

public class AccountRequestDTO {
    private BigDecimal balance;

    private String accountHolder;

    private String currency;

    public AccountRequestDTO() {
    }

    public AccountRequestDTO(BigDecimal balance, String accountHolder, String currency) {
        this.balance = balance;
        this.accountHolder = accountHolder;
        this.currency = currency;
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
