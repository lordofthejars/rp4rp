package org.acme;

import java.util.UUID;

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
    @Channel("booked")
    Emitter<String> bookings;

    @RestClient
    BillingService billingService;

    @RestClient
    PlanningService planningService;

    @POST
    @Path("/http")
    public Response bookFlightHttp(Booking booking) {
        booking = process(booking);

        billingService.sendBilling(booking);
        planningService.sendPlan(booking);

        return Response.ok(booking).build();
    }

    @POST
    @Path("/messaging")
    public Response bookFlightKafka(Booking booking) {
        booking = process(booking);
        bookings.send(JsonbBuilder.create().toJson(booking));
        return Response.ok(booking).build();
    }

    private Booking process(Booking booking) {
        return booking.setReferenceCode(UUID.randomUUID().toString());
    }
}