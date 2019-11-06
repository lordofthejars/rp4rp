package org.acme;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class OutboxEventObserver {

    public void observeOutboxEvent(@Observes OutboxEvent outboxEvent) {
        
        Outbox outbox = new Outbox();
        outbox.aggregateId = outboxEvent.getEntityId();
        outbox.content = outboxEvent.getContent();
        outbox.eventType = outboxEvent.getEventType();
        outbox.eventDate= new Date();

        outbox.persist();

    }
    
}