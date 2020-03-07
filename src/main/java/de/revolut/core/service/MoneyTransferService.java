package de.revolut.core.service;

import de.revolut.api.MoneyTransferDTO;
import de.revolut.core.exception.AccountNotFoundException;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.exception.InsufficientBalanceException;
import de.revolut.core.exception.TransactionFailedException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class MoneyTransferService { //TODO: Exception handling //TODO: TESTS

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
        accountDAO.update(senderAccount.getId(), senderAccount.getBalance().subtract(amount));
        LOGGER.info("Amount ${amount} debited from account ${senderAccount.getId()}");
        try {
            accountDAO.update(receiverAccount.getId(), receiverAccount.getBalance().add(amount));
            LOGGER.info("Amount ${amount} credited to account ${receiverAccount.getId()}");
        } catch (Exception e) { //TODO: catch exception?
            // rollback credit if debit fails;
            // if credit fails it will get out this method and transaction fails entirely
            accountDAO.update(senderAccount.getId(), senderAccount.getBalance().add(amount));
            throw new TransactionFailedException("Money transfer failed " + e.getMessage());
        }

        //TODO: write a procedure to make it in one transaction
    }
}
