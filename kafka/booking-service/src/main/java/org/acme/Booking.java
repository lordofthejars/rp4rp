package org.acme;

public class Booking {

    public Flight flight;
    public Billing billing;

    public String referenceCode;

    public Booking(Flight flight, Billing billing) {
        this.flight = flight;
        this.billing = billing;
    }

    public Flight getFlight() {
        return flight;
    }

    public Billing getBilling() {
        return billing;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public Booking setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
        return this;
    }

}