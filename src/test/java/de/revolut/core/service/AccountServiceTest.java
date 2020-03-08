package de.revolut.core.service;

import de.revolut.api.AccountRequestDTO;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
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
    public void shouldCreateAccountSuccessfully() { //TODO: modify this??

        //given
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(new BigDecimal(100), null, null);
        when(accountDAO.insert(any(Account.class))).thenReturn(1);
        when(accountDAO.getByUuid(any(UUID.class)))
                .thenReturn(new Account(UUID.randomUUID(),
                        accountRequestDTO.getBalance(), null, null));

        //when
        accountServiceUnderTest.save(accountRequestDTO);

        //then
        verify(accountDAO).insert(any(Account.class));
    }


    @Test
    public void shouldThrowExceptionWhenBalanceIsInvalid() {

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

}
