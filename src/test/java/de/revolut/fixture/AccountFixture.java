package de.revolut.fixture;

import de.revolut.api.MoneyTransferDTO;
import de.revolut.core.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountFixture {
    public static UUID receiverAccountUUID = UUID.randomUUID();

    public static UUID senderAccountUUID = UUID.randomUUID();

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(senderAccount());
        accounts.add(receiverAccount());
        return accounts;
    }

    public static Account receiverAccount() {
        return new Account(receiverAccountUUID, BigDecimal.TEN, null, null);
    }

    public static Account senderAccount() {
        return new Account(senderAccountUUID, BigDecimal.TEN, null, null);
    }

    public static MoneyTransferDTO getSuccessfulMoneyTransferDTO() {
        return new MoneyTransferDTO(receiverAccountUUID, senderAccountUUID, BigDecimal.ONE);
    }

    public static Object getUnsuccessfulMoneyTransferDTO() {
        return new MoneyTransferDTO(receiverAccountUUID, senderAccountUUID, new BigDecimal(1000));
    }
}
