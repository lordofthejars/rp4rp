package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;

@ApplicationScoped
public class BillingProducer {

    @Inject
    @Channel("billing")
    Emitter<String> billings;
    
    public void sendPayment(Billing billing) {
        this.billings.send(JsonbBuilder.create().toJson(billing));
    }
}