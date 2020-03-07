package de.revolut.resource;

import de.revolut.core.service.MoneyTransferService;
import de.revolut.db.AccountDAO;
import de.revolut.fixture.AccountFixture;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MoneyTransferResourceTest {
    private final AccountDAO accountDAO = mock(AccountDAO.class);

    @Rule
    public final ResourceTestRule moneyTransferResource = ResourceTestRule.builder()
            .addResource(new MoneyTransferResource(new MoneyTransferService(accountDAO)))
            .build();


    @Test
    public void transferSuccesfully() {
        when(accountDAO.getByUuid(AccountFixture.senderAccountUUID))
                .thenReturn(AccountFixture.senderAccount());
        when(accountDAO.getByUuid(AccountFixture.receiverAccountUUID))
                .thenReturn(AccountFixture.receiverAccount());
        when(accountDAO.update(any(), any())).thenReturn(1);

        Response response = moneyTransferResource.client()
                .target("/money-transfer")
                .request()
                .post(Entity.json(AccountFixture.getSuccessfulMoneyTransferDTO()));
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void transferWithError() {
        when(accountDAO.getByUuid(AccountFixture.senderAccountUUID))
                .thenReturn(AccountFixture.senderAccount());
        when(accountDAO.getByUuid(AccountFixture.receiverAccountUUID))
                .thenReturn(AccountFixture.receiverAccount());

        Response response = moneyTransferResource.client()
                .target("/money-transfer")
                .request()
                .post(Entity.json(AccountFixture.getUnsuccessfulMoneyTransferDTO()));
        Assert.assertEquals(400, response.getStatus());
    }

}
