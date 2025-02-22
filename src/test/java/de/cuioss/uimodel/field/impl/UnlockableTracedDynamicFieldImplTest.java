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

import de.cuioss.uimodel.field.UnlockableTracedDynamicField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Unlockable Traced Dynamic Field Tests")
class UnlockableTracedDynamicFieldImplTest {

    private static final String DEFAULT_VALUE = "default";
    private static final String SOME_VALUE = "value";

    @Nested
    @DisplayName("Value Management Tests")
    class ValueManagementTests {

        @Test
        @DisplayName("Should reset value to default")
        void shouldReset() {
            // Arrange
            UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(DEFAULT_VALUE, true);
            
            // Act
            underTest.setValue(SOME_VALUE);
            
            // Assert
            assertEquals(SOME_VALUE, underTest.getValue());
            
            // Act
            underTest.resetValue();
            
            // Assert
            assertEquals(DEFAULT_VALUE, underTest.getValue());
        }

        @Test
        @DisplayName("Should track changed state")
        void shouldServeChangedState() {
            // Arrange
            UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(DEFAULT_VALUE, true);
            
            // Act
            underTest.setValue(SOME_VALUE);
            
            // Assert
            assertTrue(underTest.isChanged());
            
            // Act
            underTest.resetValue();
            
            // Assert
            assertFalse(underTest.isChanged());
        }
    }

    @Nested
    @DisplayName("Availability Tests")
    class AvailabilityTests {

        @Test
        @DisplayName("Should determine availability based on value")
        void shouldBeAvailableOnValue() {
            // Arrange & Act
            UnlockableTracedDynamicField<String> underTest = new UnlockableTracedDynamicFieldImpl<>(null, true);
            
            // Assert
            assertFalse(underTest.isAvailable());
            
            // Act
            underTest.setValue(SOME_VALUE);
            
            // Assert
            assertTrue(underTest.isAvailable());

            // Arrange & Act
            underTest = new UnlockableTracedDynamicFieldImpl<>(SOME_VALUE, true);
            
            // Assert
            assertTrue(underTest.isAvailable());
        }
    }
}
