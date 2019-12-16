package org.acme;

public class Flight {

    public String airline;
    public String flightNumber;

    public Airport depart;
    public Airport arrive;

    public Flight(String airline, String flightNumber, Airport depart, Airport arrive) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.depart = depart;
        this.arrive = arrive;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Airport getDepart() {
        return depart;
    }

    public Airport getArrive() {
        return arrive;
    }

}