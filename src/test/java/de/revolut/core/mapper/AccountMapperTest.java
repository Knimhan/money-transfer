package de.revolut.core.mapper;

import de.revolut.core.model.Account;
import de.revolut.fixture.AccountFixture;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.SQLException;

@RunWith(MockitoJUnitRunner.class)
public class AccountMapperTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void map() throws SQLException {
        Mockito.when(resultSet.getString("id"))
                .thenReturn(AccountFixture.receiverAccountUUID.toString());
        Mockito.when(resultSet.getBigDecimal("balance"))
                .thenReturn(AccountFixture.receiverAccount().getBalance());
        Mockito.when(resultSet.getString("accountHolder")).thenReturn("test");
        Mockito.when(resultSet.getString("currency")).thenReturn("test");

        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.map(1, resultSet, null);

        Assert.assertNotNull(account);
        Assert.assertEquals(AccountFixture.receiverAccountUUID.toString(), account.getId().toString());
        Assert.assertEquals(AccountFixture.receiverAccount().getBalance(), account.getBalance());
        Assert.assertEquals("test", account.getAccountHolder());
        Assert.assertEquals("test", account.getCurrency());
    }
}
