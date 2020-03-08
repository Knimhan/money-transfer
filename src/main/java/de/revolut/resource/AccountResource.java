package de.revolut.resource;

import com.codahale.metrics.annotation.Timed;
import de.revolut.api.AccountRequestDTO;
import de.revolut.core.service.AccountService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

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

    @GET
    @Path("{accountUuid}")
    @Timed
    public Response getOne(@PathParam("accountUuid") String accountUuid) {

        return Response.ok()
                .entity(accountService.getOne(accountUuid))
                .build();
    }

    @POST
    @Timed
    public Response post(@Valid AccountRequestDTO accountRequestDTO) {

        return Response.ok()
                .entity(accountService.save(accountRequestDTO))
                .build();
    }
}
