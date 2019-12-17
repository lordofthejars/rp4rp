package org.acme;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.ReactiveMailer;

@Path("/planning")
public class PlanningResource {

    @Inject
    ReactiveMailer reactiveMailer;

    @POST
    @Path("/http")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response planFlightHttp(Booking booking) {

        Mail mailMessage = Mail.withText(getEmailFromCustomerId(booking.billing.customerId), getSubject(booking),
                "Qute.");
        reactiveMailer.send(mailMessage);

        return Response.ok().build();
    }

    private String getSubject(Booking booking) {
        return String.format("Flight Planning: %s | %s-%s", booking.flight.depart.date, booking.flight.depart.airport,
                booking.flight.arrive.airport);
    }

    private String getEmailFromCustomerId(Long customerId) {
        return customerId + "@example.com";
    }
}