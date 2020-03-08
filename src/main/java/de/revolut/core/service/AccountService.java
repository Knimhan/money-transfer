package de.revolut.core.service;

import de.revolut.api.AccountRequestDTO;
import de.revolut.api.AccountResponseDTO;
import de.revolut.core.exception.AccountCreationException;
import de.revolut.core.exception.AccountNotFoundException;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;


public class AccountService {

    private static final Logger LOGGER = getLogger(AccountService.class);

    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {

        this.accountDAO = accountDAO;
    }

    public List<AccountResponseDTO> getAll() {

        return accountDAO.getAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }


    public AccountResponseDTO getOne(String uuidString) {

        UUID uuid = null;
        try {
            uuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException exception) {
            throw new BadTransactionRequestException("Invalid uuid");
        }
        Account account = accountDAO.getByUuid(uuid);
        if (account == null) throw new AccountNotFoundException("Account with " + uuid + " does not exist");
        return map(account);
    }

    public AccountResponseDTO save(AccountRequestDTO accountRequestDTO) {

        validate(accountRequestDTO);
        UUID uuid = UUID.randomUUID();
        if (accountDAO.insert(map(uuid, accountRequestDTO)) != 1)
            throw new AccountCreationException("Account could not be created");
        LOGGER.info("Account " + uuid + " created successfully");
        return map(accountDAO.getByUuid(uuid));
    }

    private void validate(AccountRequestDTO accountRequestDTO) {

        if (accountRequestDTO.getBalance() == null || accountRequestDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadTransactionRequestException("Balance can not be null or less than zero");
        }
    }

    private AccountResponseDTO map(Account account) {

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setAccountHolder(account.getAccountHolder());
        accountResponseDTO.setCurrency(account.getCurrency());
        return accountResponseDTO;
    }

    private Account map(UUID uuid, AccountRequestDTO accountRequestDTO) {

        Account account = new Account(uuid,
                accountRequestDTO.getBalance(),
                accountRequestDTO.getAccountHolder(),
                accountRequestDTO.getCurrency());
        return account;
    }

}
