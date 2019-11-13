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
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;


@ApplicationScoped
public class NewDrinker {

    private static java.util.logging.Logger logger = Logger.getLogger(NewDrinker.class.getName());

    @Inject
    @Channel("advicer")
    Emitter<JsonObject> advices;

    @Incoming("drinker")
    public void newDrinker(JsonObject topicContent) {
        logger.info("New Drinker Event " + topicContent.toString());
        if (isCreation(topicContent)) {
            final JsonObject outboxPayload = getPayload(topicContent);
            advices.send(outboxPayload);
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