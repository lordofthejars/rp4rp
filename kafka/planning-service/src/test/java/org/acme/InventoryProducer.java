package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@ApplicationScoped
public class InventoryProducer {

    @Inject
    @Channel("itinerary")
    Emitter<String> bookings;

    public void sendBooking(Booking booking) {
        bookings.send(JsonbBuilder.create().toJson(booking));
    }
    
}