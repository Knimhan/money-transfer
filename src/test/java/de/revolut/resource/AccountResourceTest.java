package de.revolut.resource;

import de.revolut.api.AccountRequestDTO;
import de.revolut.api.AccountResponseDTO;
import de.revolut.core.model.Account;
import de.revolut.core.service.AccountService;
import de.revolut.db.AccountDAO;
import de.revolut.fixture.AccountFixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountResourceTest {

    private final AccountDAO accountDAO = mock(AccountDAO.class);

    @Rule
    public final ResourceTestRule accountResource = ResourceTestRule.builder()
            .addResource(new AccountResource(new AccountService(accountDAO)))
            .build();

    @Test
    public void getAll() {
        when(accountDAO.getAll()).thenReturn(AccountFixture.getAccounts());

        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void getOne() {
        Account account = AccountFixture.receiverAccount();
        when(accountDAO.getByUuid(account.getId())).thenReturn(account);

        Response response = accountResource.client()
                .target("/accounts/" + account.getId())
                .request()
                .get();

        Assert.assertEquals(200, response.getStatus());
        AccountResponseDTO accountResponseDTO = response.readEntity(AccountResponseDTO.class);
        Assert.assertEquals(account.getId(), accountResponseDTO.getId());
        Assert.assertEquals(account.getBalance(), account.getBalance());
    }

    @Test
    public void save() {
        Account account = AccountFixture.receiverAccount();
        when(accountDAO.insert(any(Account.class))).thenReturn(1);
        when(accountDAO.getByUuid(any(UUID.class))).thenReturn(account);

        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(BigDecimal.TEN, null, null)));

        Assert.assertEquals(200, response.getStatus());
        AccountResponseDTO accountResponseDTO = response.readEntity(AccountResponseDTO.class);
        Assert.assertEquals(account.getId(), accountResponseDTO.getId());
        Assert.assertEquals(account.getBalance(), accountResponseDTO.getBalance());
    }

    @Test
    public void saveContractFailedDueToBalanceNull() {
        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(null, "test", "EUR")));

        Assert.assertEquals(422, response.getStatus());
        Assert.assertTrue(response.readEntity(String.class).contains("balance may not be null"));
    }

    @Test
    public void saveContractFailedDueToInvalidAccountholderName() {
        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(BigDecimal.ONE,
                        "test123456test123456test123456test123456test123456test123456",
                        "EUR")));

        Assert.assertEquals(422, response.getStatus());
        Assert.assertTrue(response.readEntity(String.class).contains("accountHolder length must be between 0 and 50"));
    }

    @Test
    public void saveContractFailedDueToInvalidCurrency() {
        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(BigDecimal.ONE,
                        "test",
                        "EUROPATESTCURRENCY")));

        Assert.assertEquals(422, response.getStatus());
        Assert.assertTrue(response.readEntity(String.class).contains("currency length must be between 0 and 5"));
    }
}
