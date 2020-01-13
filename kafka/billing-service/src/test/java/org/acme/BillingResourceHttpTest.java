package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@QuarkusTest
public class BillingResourceHttpTest {

    @Inject
    PaymentStub paymentStub;

    @Test
    public void testPayment() {

        // Given
        final Billing billing = BillingMother.complete();

        // When
        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(billing))
                .when()
                .post("/billing/http")
                .then()
                .statusCode(200);

        //  Then
        final Optional<Billing> expectedBilling = paymentStub.getBillingForCustomerId(billing.customerId);
        assertThat(expectedBilling).isPresent();
    }

}