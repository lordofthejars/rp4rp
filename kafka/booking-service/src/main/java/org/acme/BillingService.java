package org.acme;

import java.util.concurrent.CompletionStage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/billing/http")
public interface BillingService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void sendBilling(Billing billing);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    CompletionStage<Response> sendBillingAsync(Billing billing);

}