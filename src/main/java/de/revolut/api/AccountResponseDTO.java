package de.revolut.api;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountResponseDTO {
    private UUID id;

    //TODO : If holder details added, add here as well
    private BigDecimal balance;

    public AccountResponseDTO() {
    }

    public AccountResponseDTO(UUID id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
