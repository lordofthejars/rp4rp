package org.acme;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit5.HoverflyExtension;
import io.specto.hoverfly.junit5.api.HoverflyConfig;
import io.specto.hoverfly.junit5.api.HoverflyCore;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ExtendWith(HoverflyExtension.class)
@HoverflyCore(config = @HoverflyConfig(proxyLocalHost = true, destination = "localhost:809+"))
public class BookingResourceHttpTest {

    @Inject
    ServiceVirtualizationSimulation serviceVirtualizationSimulation;

    @Test
    public void testBookFlight(Hoverfly hoverfly) {

        // Given

        final Booking booking = BookingMother.complete();

        serviceVirtualizationSimulation.record(hoverfly, booking);

        // When

        String response = given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(booking))
                .when()
                .post("/book/http")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        // Then

        final Booking booked = JsonbBuilder.create().fromJson(response, Booking.class);

        final Billing bill = booked.billing;
        assertThat(bill.getCustomerId()).isEqualTo(123L);
        assertThat(bill.getPrice()).isEqualTo(1250D);

        assertThat(booked.referenceCode).isNotBlank();

        serviceVirtualizationSimulation.verifyBilling(hoverfly);
        serviceVirtualizationSimulation.verifyItinerary(hoverfly);

    }

}