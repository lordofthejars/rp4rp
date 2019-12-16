package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeBookingResourceIT extends BookingResourceTest {

    // Execute the same tests but in native mode.
}