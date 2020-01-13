package org.acme;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Channel;

@Path("/book")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    @Channel("billing")
    Emitter<String> billing;

    @Inject
    @Channel("itinerary")
    Emitter<String> bookings;

    @RestClient
    BillingService billingService;

    @RestClient
    PlanningService planningService;

    @POST
    @Path("/http")
    public Response bookFlightHttp(Booking booking) {
        booking = process(booking);

        planningService.sendPlan(booking);
        billingService.sendBilling(booking.billing);

        return Response.ok(booking).build();
    }

    @POST
    @Path("/messaging")
    public Response bookFlightKafka(Booking booking) {
        booking = process(booking);
        bookings.send(JsonbBuilder.create().toJson(booking));
        billing.send(JsonbBuilder.create().toJson(booking.billing));

        return Response.ok(booking).build();
    }

    private Booking process(Booking booking) {
        return booking.setReferenceCode(UUID.randomUUID().toString());
    }
}