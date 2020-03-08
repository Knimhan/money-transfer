package de.revolut.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class MoneyTransferDTO {

    @JsonProperty
    @NotNull
    private UUID receiverAccountUuid;

    @JsonProperty
    @NotNull
    private UUID senderAccountUuid;

    @JsonProperty
    @NotNull
    private BigDecimal amount;

    public MoneyTransferDTO() {
    }

    public MoneyTransferDTO(UUID receiverAccountUuid, UUID senderAccountUuid, BigDecimal amount) {
        this.receiverAccountUuid = receiverAccountUuid;
        this.senderAccountUuid = senderAccountUuid;
        this.amount = amount;
    }

    public UUID getReceiverAccountUuid() {
        return receiverAccountUuid;
    }

    public UUID getSenderAccountUuid() {
        return senderAccountUuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
