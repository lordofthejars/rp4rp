package org.acme;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/billing/http")
public interface BillingService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void sendBilling(Billing billing);

}