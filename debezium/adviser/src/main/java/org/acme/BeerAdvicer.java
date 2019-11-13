package org.acme;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.ReactiveMailer;

@ApplicationScoped
public class BeerAdvicer {

    private static java.util.logging.Logger logger = Logger.getLogger(BeerAdvicer.class.getName());

    @Inject
    ReactiveMailer reactiveMailer;

    @Incoming("advicer")
    public void findBestBeersForUser(JsonObject newDrinker) {
        logger.info("New Advice Event " + newDrinker.toString());

        Mail mailMessage = Mail.withText(newDrinker.getString("email"), "Best Beers for you", "We think that you should buy these beers.");
        reactiveMailer.send(mailMessage);

    }
    
}