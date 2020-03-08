package de.revolut.resource;

import com.codahale.metrics.annotation.Timed;
import de.revolut.api.AccountRequestDTO;
import de.revolut.core.service.AccountService;

import javax.ws.rs.*;
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

    @GET
    @Path("{uuid}")
    @Timed
    public Response getOne(@PathParam("uuid") String uuid) {

        return Response.ok()
                .entity(accountService.getOne(uuid))
                .build();
    }

    //TODO : Question : Should we also have something /accounts/{accId} to get accountDetails for individual account.

}
