package org.acme;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@Path("/billing")
public class BillingResource {

    @Inject
    Payment paymentGateway;

    @Incoming("billing")
    public void proceedWithPayment(String billingMessage) {
        final Billing billing = JsonbBuilder.create().fromJson(billingMessage, Billing.class);
        paymentGateway.pay(billing);
    }

    @POST
    @Path("/http")
    @Produces(MediaType.APPLICATION_JSON)
    public Response payFlightHttp(Billing billing) {
        paymentGateway.pay(billing);
        return Response.ok().build();
    }
}