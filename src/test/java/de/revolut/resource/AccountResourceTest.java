package de.revolut.resource;

import de.revolut.api.AccountRequestDTO;
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
        Assert.assertEquals(200, response.getStatus()); //TODO: check body
    }

    @Test
    public void getOne() {
        when(accountDAO.getByUuid(AccountFixture.receiverAccountUUID))
                .thenReturn(AccountFixture.receiverAccount());
        Response response = accountResource.client()
                .target("/accounts/" + AccountFixture.receiverAccountUUID)
                .request()
                .get();
        Assert.assertEquals(200, response.getStatus()); //TODO: check body
    }

    @Test
    public void save() {
        when(accountDAO.insert(any(Account.class))).thenReturn(1);
        when(accountDAO.getByUuid(any(UUID.class))).thenReturn(AccountFixture.receiverAccount());

        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(BigDecimal.TEN, null, null)));
        Assert.assertEquals(200, response.getStatus());
    }
}
