package org.acme;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/billing")
public class BillingResource {

    @Inject
    PaymentGateway paymentGateway;

    @POST
    @Path("/http")
    @Produces(MediaType.APPLICATION_JSON)
    public Response payFlightHttp(Billing billing) {
        paymentGateway.pay(billing);
        return Response.ok().build();
    }
}