package org.acme;

import java.util.concurrent.CompletionStage;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/planning/http")
public interface PlanningService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void sendPlan(Booking booking);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    CompletionStage<Response> sendPlanAsync(Booking booking);

}