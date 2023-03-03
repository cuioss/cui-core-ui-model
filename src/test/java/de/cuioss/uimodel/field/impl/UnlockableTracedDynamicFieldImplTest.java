package de.cuioss.uimodel.field.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.uimodel.field.UnlockableTracedDynamicField;

class UnlockableTracedDynamicFieldImplTest {

    private static final String DEFAULT_VALUE = "default";
    private static final String SOME_VALUE = "value";

    @Test
    void shouldReset() {
        UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(DEFAULT_VALUE, true);
        underTest.setValue(SOME_VALUE);
        assertEquals(SOME_VALUE, underTest.getValue());
        underTest.resetValue();
        assertEquals(DEFAULT_VALUE, underTest.getValue());
    }

    @Test
    void shouldServeChangedState() {
        UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(DEFAULT_VALUE, true);
        underTest.setValue(SOME_VALUE);
        assertTrue(underTest.isChanged());
        underTest.resetValue();
        assertFalse(underTest.isChanged());
    }

    @Test
    void shouldBeAvailableOnValue() {
        UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(null, true);
        assertFalse(underTest.isAvailable());
        underTest.setValue(SOME_VALUE);
        assertTrue(underTest.isAvailable());

        underTest = new UnlockableTracedDynamicFieldImpl<>(SOME_VALUE, true);
        assertTrue(underTest.isAvailable());
    }
}
