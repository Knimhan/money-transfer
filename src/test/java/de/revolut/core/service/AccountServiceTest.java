package de.revolut.core.service;

import de.revolut.api.AccountRequestDTO;
import de.revolut.core.exception.AccountCreationException;
import de.revolut.core.exception.AccountNotFoundException;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import de.revolut.fixture.AccountFixture;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    private AccountService accountServiceUnderTest;

    @Before
    public void init() {

        initMocks(this);
        accountServiceUnderTest = new AccountService(accountDAO);

    }

    @Test
    public void shouldCreateAccountSuccessfully() {

        //given
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(new BigDecimal(100), null, null);
        Account account = AccountFixture.senderAccount();
        when(accountDAO.insert(any(Account.class))).thenReturn(1);
        when(accountDAO.getByUuid(any(UUID.class))).thenReturn(account);

        //when
        accountServiceUnderTest.save(accountRequestDTO);

        //then
        Mockito.verify(accountDAO, times(1))
                .insert(ArgumentCaptor.forClass(Account.class).capture());
    }


    @Test
    public void shouldThrowExceptionWhenBalanceIsNegative() {
        //given
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(new BigDecimal(-10), null, null);

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> accountServiceUnderTest.save(accountRequestDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Balance can not be null or less than zero",
                exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenBalanceIsNull() {
        //given
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(null, null, null);

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> accountServiceUnderTest.save(accountRequestDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Balance can not be null or less than zero",
                exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUuidIsWrong() {
        //given
        String uuid = "wrong-uuid";

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> accountServiceUnderTest.getOne(uuid));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Invalid uuid",
                exception.getMessage());
    }


    @Test
    public void shouldThrowExceptionWhenCreationFails() {
        //given
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(new BigDecimal(100), null, null);
        when(accountDAO.insert(any(Account.class))).thenReturn(0);
        when(accountDAO.getByUuid(any(UUID.class)))
                .thenReturn(new Account(UUID.randomUUID(),
                        accountRequestDTO.getBalance(), null, null));

        //when
        Throwable exception = assertThrows(AccountCreationException.class,
                () -> accountServiceUnderTest.save(accountRequestDTO));

        //then
        assertEquals("Money transfer application failed to create account: " +
                        "Account could not be created",
                exception.getMessage());

    }

    @Test
    public void shouldThrowExceptionWhenAccountNotFound() {
        //given
        UUID uuid = UUID.randomUUID();
        when(accountDAO.getByUuid(uuid)).thenReturn(null);

        //when
        Throwable exception = assertThrows(AccountNotFoundException.class,
                () -> accountServiceUnderTest.getOne(uuid.toString()));

        //then
        assertEquals("Money transfer application can not find account: " +
                        "Account with " + uuid + " does not exist",
                exception.getMessage());
    }

}
