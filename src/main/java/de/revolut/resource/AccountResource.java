package de.revolut.resource;

import com.codahale.metrics.annotation.Timed;
import de.revolut.api.AccountRequestDTO;
import de.revolut.core.service.AccountService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    //TODO: make sure idempotent all
    //TODO: add Validation @NotNull @Valid etc
    //TODO: ERROR DTO

    private AccountService accountService;

    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Timed
    public Response getAll() {

        return Response.ok()
                .entity(accountService.getAll())
                .build();
    }

    @POST
    @Timed
    public Response post(AccountRequestDTO accountRequestDTO) {

        return Response.ok()
                .entity(accountService.save(accountRequestDTO))
                .build();
    }
}
