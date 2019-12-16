package org.acme;

import java.time.LocalDate;
import java.time.LocalTime;

public class Airport {

    public String depart;
    public String arrive;
    public LocalDate date;
    public LocalTime time;
    public String terminal;

    public Airport(String depart, String arrive, LocalDate date, LocalTime time, String terminal) {
        this.depart = depart;
        this.arrive = arrive;
        this.date = date;
        this.time = time;
        this.terminal = terminal;
    }

    public String getDepart() {
        return depart;
    }

    public String getArrive() {
        return arrive;
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