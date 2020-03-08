package de.revolut.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountRequestDTO {
    @NotNull
    @JsonProperty
    private BigDecimal balance;

    @Length(max = 50)
    @JsonProperty
    private String accountHolder;

    @Length(max = 5)
    @JsonProperty
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
