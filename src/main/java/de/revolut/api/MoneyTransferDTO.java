package de.revolut.api;

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
