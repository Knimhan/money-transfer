package de.revolut.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class MoneyTransferDTO {

    private UUID receiverAccountUuid;

    private UUID senderAccountUuid;

    private BigDecimal amount;

    public MoneyTransferDTO() {
    }

    public MoneyTransferDTO(UUID receiverAccountUuid, UUID senderAccountUuid, BigDecimal amount) {
        this.receiverAccountUuid = receiverAccountUuid;
        this.senderAccountUuid = senderAccountUuid;
        this.amount = amount;
    }

    @JsonProperty
    public UUID getReceiverAccountUuid() {
        return receiverAccountUuid;
    }

    @JsonProperty
    public UUID getSenderAccountUuid() {
        return senderAccountUuid;
    }

    @JsonProperty
    public BigDecimal getAmount() {
        return amount;
    }
}
