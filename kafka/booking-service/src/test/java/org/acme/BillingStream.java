package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BillingStream {

    private String message;

    @Incoming("billing")
    public void billing(String billing) {
        this.message = billing;
    }
    
    public Billing getBilling() {
        if (message != null) {
            return JsonbBuilder.create()
                        .fromJson(this.message, Billing.class);
        }

        return null;
    }

}