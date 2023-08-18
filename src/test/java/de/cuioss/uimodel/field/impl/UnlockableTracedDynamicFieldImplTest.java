/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
