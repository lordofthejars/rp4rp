package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class BookingResourceTest {

    @Inject
    BillingStream billingStream;

    @Test
    public void testHelloEndpoint() {

        // Given
        
        final FlightDetail flightDepartureDetail = new FlightDetail("BCN", LocalDate.of(2019, 1, 1), LocalTime.of(0, 0, 0), "T1");
        final FlightDetail flightArrivalDetail = new FlightDetail("AUS", LocalDate.of(2019, 1, 1), LocalTime.of(8, 5, 0), "T");

        final Flight flight = new Flight("BA", "BA123", flightDepartureDetail, flightArrivalDetail);
        final Billing billing = new Billing(123L, 1250D);
        final Booking booking = new Booking(flight, billing);

        // When

        given()
          .contentType(ContentType.JSON)
          .body(JsonbBuilder.create().toJson(booking))
          .when()
          .post("/book/messaging")
          .then()
            .statusCode(200);

        // Then

        await().atMost(2, SECONDS)
               .until(() -> billingStream.getBilling() != null);

        final Billing bill = billingStream.getBilling();
        assertThat(bill.getCustomerId()).isEqualTo(123L);
        assertThat(bill.getPrice()).isEqualTo(1250D);

    }

}