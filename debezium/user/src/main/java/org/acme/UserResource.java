package org.acme;

import java.net.URI;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("/user")
public class UserResource {

    @Inject
    Event<OutboxEvent> outboxEvent;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@Valid User user) {
        
        // Persists entity
        user.id = null;
        user.persist();

        // Notifies the creation to Outbox
        outboxEvent.fire(createOutboxEvent(user));

        // Return created instance
        URI location = UriBuilder
                            .fromResource(UserResource.class)
                            .path(Long.toString(user.id))
                            .build();        
        return Response
                .created(location)
                .entity(user)
                .build();
    }

    @GET
    @Path("/outbox")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> outbox() {
        return Outbox.findAll().list();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> users() {
        return User.findAll().list();
    }

    private OutboxEvent createOutboxEvent(User user) {
        JsonObject userContent = Json.createObjectBuilder()
            .add("email", user.email)
            .add("age", user.age)
            .build();
        
        return new OutboxEvent(user.id, "USER_CREATED", userContent);
    }

}