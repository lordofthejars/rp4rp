package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeBillingResourceIT extends BillingResourceTest {

    // Execute the same tests but in native mode.
}