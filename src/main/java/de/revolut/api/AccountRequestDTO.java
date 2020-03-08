package de.revolut.api;

import java.math.BigDecimal;

public class AccountRequestDTO {
    private BigDecimal balance;

    public AccountRequestDTO() {
    }

    //TODO : Add account Holder details
    // Add currency
    public AccountRequestDTO(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
