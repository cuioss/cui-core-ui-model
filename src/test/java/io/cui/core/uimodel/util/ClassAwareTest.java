package io.cui.core.uimodel.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ClassAwareTest {

    @Test
    void testIsProperType() {
        final var string = "This is a test";

        assertFalse(ClassAware.isProperType(this.getClass(), string));
    }

}
