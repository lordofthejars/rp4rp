package org.acme;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingMother {

    public static Booking complete() {
        final FlightDetail flightDepartureDetail = new FlightDetail("BCN", LocalDate.of(2019, 1, 1),
                LocalTime.of(0, 0, 0), "T1");
        final FlightDetail flightArrivalDetail = new FlightDetail("AUS", LocalDate.of(2019, 1, 1),
                LocalTime.of(8, 5, 0), "T");

        final Flight flight = new Flight("BA", "BA123", flightDepartureDetail, flightArrivalDetail);
        final Billing billing = new Billing(123L, 1250D);
        final Booking booking = new Booking(flight, billing);

        return booking;
    }

    
}