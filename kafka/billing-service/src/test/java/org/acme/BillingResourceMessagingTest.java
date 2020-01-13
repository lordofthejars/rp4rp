package org.acme;

import io.quarkus.test.junit.QuarkusTest;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class BillingResourceMessagingTest {

    @Inject
    BillingProducer billingProducer;

    @Inject
    PaymentStub paymentStub;

    @Test
    public void testPayment() {

        // Given
        final Billing billing = BillingMother.complete();

        // When
        billingProducer.sendPayment(billing);

        // Then
        await().atMost(2, SECONDS)
                .until(() -> paymentStub.getBillingForCustomerId(billing.customerId).isPresent());
        final Billing createdBilling = paymentStub.getBillingForCustomerId(billing.customerId).get();
        
        assertThat(createdBilling.customerId).isEqualTo(billing.customerId);
        assertThat(createdBilling.price).isEqualTo(billing.price);

    }

}