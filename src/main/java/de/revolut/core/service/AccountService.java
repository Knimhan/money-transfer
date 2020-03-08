package de.revolut.core.service;

import de.revolut.api.AccountRequestDTO;
import de.revolut.core.exception.AccountCreationException;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;


public class AccountService {

    private static final Logger LOGGER = getLogger(AccountService.class);

    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public List<Account> getAll() {

        return accountDAO.getAll();
    }

    public Account save(AccountRequestDTO accountRequestDTO) {

        validate(accountRequestDTO);
        UUID uuid = UUID.randomUUID();
        if (accountDAO.insert(new Account(uuid, accountRequestDTO.getBalance())) != 1)
            throw new AccountCreationException("Account could not be created");
        LOGGER.info("New account created ${uuid}");
        return accountDAO.getByUuid(uuid);
    }

    private void validate(AccountRequestDTO accountRequestDTO) {

        if (accountRequestDTO.getBalance() == null || accountRequestDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadTransactionRequestException("Balance cant not be null or less than zero");
        }
    }
}
