package org.acme;

import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class PlanningResourceHttpTest {
    
    @Inject
    MockMailbox mailbox;

    @Test
    public void testPlanItinerary() {

        // Given
        final Booking booking = BookingMother.complete();

        // When
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(booking))
                .when()
                .post("/planning/http")
                .then()
                .statusCode(200);

        // Then
        assertThat(mailbox.getMessagesSentTo(booking.billing.customerId + "@example.com"))
                    .isNotNull();
    }
    
}