package org.acme;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.json.bind.annotation.JsonbDateFormat;

public class FlightDetail {

    public String airport;
    public LocalDate date;

    @JsonbDateFormat("HH:mm")
    public LocalTime time;
    
    public String terminal;

    public FlightDetail() {
    }

    public FlightDetail(String airport, LocalDate date, LocalTime time, String terminal) {
        this.airport = airport;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
    }

    public String getAirport() {
        return airport;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getTerminal() {
        return terminal;
    }

}