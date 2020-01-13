package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class BookingResourceMessagingTest {

    @Inject
    BillingStream billingStream;

    @Inject
    ItineraryStream bookingStream;

    @Test
    public void testBookFlight() {

        // Given
        
       final Booking booking = BookingMother.complete();

        // When

        given()
          .contentType(ContentType.JSON)
          .body(JsonbBuilder.create().toJson(booking))
          .when()
          .post("/book/messaging")
          .then()
            .statusCode(200);

        // Then

        // Polls every 50ms
        await().atMost(2, SECONDS)
                .until(() -> billingStream.getBilling() != null);
               
        await().atMost(2, SECONDS)
                .until(() -> bookingStream.getBooking() != null);

        final Billing bill = billingStream.getBilling();
        assertThat(bill.getCustomerId()).isEqualTo(123L);
        assertThat(bill.getPrice()).isEqualTo(1250D);

        final Booking book = bookingStream.getBooking();
        assertThat(book.referenceCode).isNotBlank();

    }

}