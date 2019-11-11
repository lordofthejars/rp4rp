package org.acme;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeAdviceResourceIT extends AdviceResourceTest {

    // Execute the same tests but in native mode.
}