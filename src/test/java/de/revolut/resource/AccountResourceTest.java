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
    public void save() {
        when(accountDAO.insert(any(Account.class))).thenReturn(1);
        Response response = accountResource.client()
                .target("/accounts")
                .request()
                .post(Entity.json(new AccountRequestDTO(BigDecimal.TEN)));
        Assert.assertEquals(200, response.getStatus());
    }
}
