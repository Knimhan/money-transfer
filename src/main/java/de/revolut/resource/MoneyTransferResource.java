package de.revolut.resource;

import com.codahale.metrics.annotation.Timed;
import de.revolut.api.MoneyTransferDTO;
import de.revolut.core.service.MoneyTransferService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/money-transfer")
@Produces(MediaType.APPLICATION_JSON)
public class MoneyTransferResource {

    private MoneyTransferService moneyTransferService;

    public MoneyTransferResource() {
    }

    public MoneyTransferResource(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @POST
    @Timed
    public Response transfer(MoneyTransferDTO moneyTransferDTO) {
        //TODO: return??
        moneyTransferService.transfer(moneyTransferDTO);
        return Response.ok().build();
    }
}
