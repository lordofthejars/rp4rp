package org.acme;

public class BillingMother {

    public static Billing complete() {
        return new Billing(123L, 10.10D);
    }
    
}