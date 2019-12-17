package org.acme;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentGateway {

    public void pay(Billing billing) {
        System.out.printf("Payment of %f done for user %d%n", billing.price, billing.customerId);
    }
    
}