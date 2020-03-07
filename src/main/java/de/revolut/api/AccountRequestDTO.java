package de.revolut.api;

import java.math.BigDecimal;

public class AccountRequestDTO {
    private BigDecimal balance;

    public AccountRequestDTO() {
    }

    public AccountRequestDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
