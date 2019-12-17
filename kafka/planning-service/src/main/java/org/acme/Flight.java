package org.acme;

public class Flight {

    public String airline;
    public String flightNumber;

    public FlightDetail depart;
    public FlightDetail arrive;

    public Flight() {
    }

    public Flight(String airline, String flightNumber, FlightDetail depart, FlightDetail arrive) {
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

    public FlightDetail getDepart() {
        return depart;
    }

    public FlightDetail getArrive() {
        return arrive;
    }

}