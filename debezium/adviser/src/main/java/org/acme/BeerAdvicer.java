package org.acme;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.ReactiveMailer;


@ApplicationScoped
public class BeerAdvicer {

    private static java.util.logging.Logger logger = Logger.getLogger(BeerAdvicer.class.getName());

    @Inject
    ReactiveMailer reactiveMailer;

    @Incoming("drinker")
    public void newDrinker(JsonObject topicContent) {
        logger.info("New Drinker Event " + topicContent.toString());
        if (isCreation(topicContent)) {
            final JsonObject outboxPayload = getPayload(topicContent);
            Mail mailMessage = Mail.withText(outboxPayload.getString("email"), "Best Beers for you", "We think that you should buy these beers.");
            reactiveMailer.send(mailMessage);
        }
    }
    
    private JsonObject getPayload(JsonObject topicContent) {
        final JsonObject after = topicContent.getJsonObject("after");
        final String message = after.getString("content");
            return Json.createReader(new StringReader(message)).readObject();
    }

    private boolean isCreation(JsonObject topicContent) {
        return topicContent.getString("op").startsWith("c");
    }

}