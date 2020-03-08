package de.revolut.core.service;

import de.revolut.api.MoneyTransferDTO;
import de.revolut.core.exception.BadTransactionRequestException;
import de.revolut.core.exception.InsufficientBalanceException;
import de.revolut.core.model.Account;
import de.revolut.db.AccountDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MoneyTransferServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MoneyTransferService moneyTransferServiceUnderTest;

    @Before
    public void init() {

        initMocks(this);
        moneyTransferServiceUnderTest = new MoneyTransferService(accountDAO);

    }


    @Test
    public void shouldThrowExceptionWhenMoneyTransferDTOHasInvalidAmount() {

        //given
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO();

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> moneyTransferServiceUnderTest.transfer(moneyTransferDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Amount to be transferred can not be null or negative",
                exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenMoneyTransferDTOHasInvalidSenderAccount() {

        //given
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO(UUID.randomUUID(),
                null, BigDecimal.TEN);

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> moneyTransferServiceUnderTest.transfer(moneyTransferDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Sender account can not be null",
                exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenMoneyTransferDTOHasInvalidReciverAccount() {

        //given
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO(null,
                UUID.randomUUID(), BigDecimal.TEN);

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> moneyTransferServiceUnderTest.transfer(moneyTransferDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Receiver account can not be null",
                exception.getMessage());
    }


    @Test
    public void shouldThrowExceptionWhenMoneyTransferDTOHasSameSenderReciverAccount() {

        //given
        UUID uuid = UUID.randomUUID();
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO(uuid, uuid, BigDecimal.TEN);

        //when
        Throwable exception = assertThrows(BadTransactionRequestException.class,
                () -> moneyTransferServiceUnderTest.transfer(moneyTransferDTO));

        //then
        assertEquals("Money transfer application has failed due to wrong inputs: " +
                        "Sender and receiver account can not be same",
                exception.getMessage());
    }


    @Test
    public void shouldThrowExceptionWhenBalanceIsInvalid() {

        //given
        UUID senderAccountUuid = UUID.randomUUID();
        UUID receiverAccountUuid = UUID.randomUUID();
        BigDecimal amountTobeTransferred = new BigDecimal(100);
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO(receiverAccountUuid,
                senderAccountUuid, amountTobeTransferred);
        //when
        when(accountDAO.getByUuid(receiverAccountUuid)).thenReturn(new Account(receiverAccountUuid, new BigDecimal(50)));
        when(accountDAO.getByUuid(senderAccountUuid)).thenReturn(new Account(senderAccountUuid, new BigDecimal(50)));
        Throwable exception = assertThrows(InsufficientBalanceException.class,
                () -> moneyTransferServiceUnderTest.transfer(moneyTransferDTO));

        //then
        assertEquals("Money transfer application has failed due to: " +
                        "Insufficient funds at senders account to make the transfer",
                exception.getMessage());
    }

    @Test
    public void shouldTransferMoneySuccessfully() {
        //given
        UUID senderAccountUuid = UUID.randomUUID();
        UUID receiverAccountUuid = UUID.randomUUID();
        BigDecimal amountTobeTransferred = new BigDecimal(10);
        MoneyTransferDTO moneyTransferDTO = new MoneyTransferDTO(receiverAccountUuid,
                senderAccountUuid, amountTobeTransferred);
        Account receiverAccount = new Account(receiverAccountUuid, new BigDecimal(50));
        Account senderAccount = new Account(senderAccountUuid, new BigDecimal(50));
        //when
        when(accountDAO.getByUuid(receiverAccountUuid)).thenReturn(receiverAccount);
        when(accountDAO.getByUuid(senderAccountUuid)).thenReturn(senderAccount);
        moneyTransferServiceUnderTest.transfer(moneyTransferDTO);

        //then
        verify(accountDAO).transfer(receiverAccount, senderAccount, amountTobeTransferred);
    }
}
