package de.revolut.core.service;

import de.revolut.api.MoneyTransferDTO;
import de.revolut.core.exception.AccountNotFoundException;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.exception.InsufficientBalanceException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class MoneyTransferService {

    private static final Logger LOGGER = getLogger(MoneyTransferService.class);

    private AccountDAO accountDAO;

    public MoneyTransferService(AccountDAO accountDAO) {

        this.accountDAO = accountDAO;
    }

    public void transfer(MoneyTransferDTO moneyTransferDTO) {

        validate(moneyTransferDTO);

        transfer(getByUuid(moneyTransferDTO.getReceiverAccountUuid()),
                getByUuid(moneyTransferDTO.getSenderAccountUuid()),
                moneyTransferDTO.getAmount());
    }

    private Account getByUuid(UUID uuid) {

        Account account = accountDAO.getByUuid(uuid);
        if (account == null) throw new AccountNotFoundException("Account with " + uuid + " does not exist");
        return account;
    }


    private void validate(MoneyTransferDTO moneyTransferDTO) {
        if (moneyTransferDTO.getAmount() == null || moneyTransferDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadTransactionRequestException("Amount to be transferred can not be null or negative");
        }
        if (moneyTransferDTO.getSenderAccountUuid() == null) {
            throw new BadTransactionRequestException("Sender account can not be null");
        }
        if (moneyTransferDTO.getReceiverAccountUuid() == null) {
            throw new BadTransactionRequestException("Receiver account can not be null");
        }
        if (moneyTransferDTO.getSenderAccountUuid() == moneyTransferDTO.getReceiverAccountUuid()) {
            throw new BadTransactionRequestException("Sender and receiver account can not be same");
        }
    }

    private synchronized void transfer(Account receiverAccount,
                                       Account senderAccount,
                                       BigDecimal amount) {
        if (senderAccount.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Insufficient funds at senders account to make the transfer");
        }
        accountDAO.transfer(receiverAccount, senderAccount, amount);
    }
}
