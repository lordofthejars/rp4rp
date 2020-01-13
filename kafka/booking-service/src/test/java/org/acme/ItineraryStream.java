package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class ItineraryStream {

    private String message;

    @Incoming("itinerary")
    public void booked(String bookedFlight) {
        this.message = bookedFlight;
    }
    
    public Booking getBooking() {
        if (message != null) {
            return JsonbBuilder.create().fromJson(message, Booking.class);
        }

        return null;
    }

}