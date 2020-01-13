package org.acme;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.JsonbBuilder;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.verification.HoverflyVerifications;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.matchesPartialJson;


@ApplicationScoped
public class ServiceVirtualizationSimulation {

    public void record(Hoverfly hoverfly, Booking booking) {
        String response = JsonbBuilder.create().toJson(booking);
        hoverfly.simulate(
            dsl(service("localhost:8090").post("/planning/http")
                .body(matchesPartialJson(response)).willReturn(success())),
            dsl(service("localhost:8091").post("/billing/http")
                .body(JsonbBuilder.create().toJson(booking.billing)).willReturn(success())));
    }
    
    public void verifyItinerary(Hoverfly hoverfly) {
        hoverfly.verify(service("localhost:8090")
                .post("/planning/http")
                .anyBody(), HoverflyVerifications.times(1));
    }

    public void verifyBilling(Hoverfly hoverfly) {
        hoverfly.verify(service("localhost:8091").post("/billing/http").anyBody(), HoverflyVerifications.times(1));
    }

}