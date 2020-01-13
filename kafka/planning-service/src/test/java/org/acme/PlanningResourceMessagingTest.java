package org.acme;

import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

@QuarkusTest
public class PlanningResourceMessagingTest {

    @Inject
    MockMailbox mailbox;

    @Inject
    InventoryProducer inventoryProducer;

    @Test
    public void testPlanItinerary() {

        // Given
        final Booking booking = BookingMother.complete();

        // When
        inventoryProducer.sendBooking(booking);

        // Then
        await().atMost(2, SECONDS)
                .until(() -> mailbox.getTotalMessagesSent() > 1);
            
        assertThat(mailbox.getMessagesSentTo(booking.billing.customerId + "@example.com"))
                    .isNotNull();

    }

}