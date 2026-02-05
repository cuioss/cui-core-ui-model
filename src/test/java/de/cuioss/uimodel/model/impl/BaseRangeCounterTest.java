/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.model.impl;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.contracts.VerifyCopyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.model.RangeCounter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests BaseRangeCounter Implementation")
@PropertyReflectionConfig(of = {"count", "totalCount"})
@VerifyConstructor(of = {"count", "totalCount"})
@VerifyCopyConstructor(argumentType = RangeCounter.class)
class BaseRangeCounterTest extends ValueObjectTest<BaseRangeCounter> {

    @Nested
    @DisplayName("Constructor and Copy Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create and copy with valid counts")
        void shouldCreateAndCopyWithValidCounts() {
            // Arrange
            final var count = 10;
            final var totalCount = 100;

            // Act
            final var baseRangeCounter = new BaseRangeCounter(count, totalCount);
            final var copiedCounter = new BaseRangeCounter(baseRangeCounter);

            // Assert
            assertEquals(count, copiedCounter.getCount());
            assertTrue(copiedCounter.isCountAvailable());
            assertTrue(baseRangeCounter.isTotalCountAvailable());
            assertFalse(baseRangeCounter.isSingleValueOnly());
            assertTrue(baseRangeCounter.isComplete());
            assertFalse(baseRangeCounter.isEmpty());
        }

        @Test
        @DisplayName("Should handle null values")
        void shouldHandleNullValues() {
            // Arrange & Act
            final var baseRangeCounter = new BaseRangeCounter(null, null);

            // Assert
            assertFalse(baseRangeCounter.isCountAvailable());
            assertFalse(baseRangeCounter.isTotalCountAvailable());
            assertFalse(baseRangeCounter.isSingleValueOnly());
            assertTrue(baseRangeCounter.isEmpty());
            assertFalse(baseRangeCounter.isComplete());
        }

        @Test
        @DisplayName("Should handle zero values")
        void shouldHandleZeroValues() {
            // Arrange & Act
            final var baseRangeCounter = new BaseRangeCounter(0, 0);

            // Assert
            assertTrue(baseRangeCounter.isCountAvailable());
            assertFalse(baseRangeCounter.isEmpty());
            assertFalse(baseRangeCounter.isSingleValueOnly());
            assertTrue(baseRangeCounter.isComplete());
        }
    }

    @Nested
    @DisplayName("Single Value Tests")
    class SingleValueTests {

        @ParameterizedTest(name = "count={0}, totalCount={1}")
        @DisplayName("Should detect single value cases")
        @CsvSource({
                "1, ''",
                "'', 0"
        })
        void shouldDetectSingleValue(String count, String totalCount) {
            // Arrange & Act
            final var baseRangeCounter = new BaseRangeCounter(
                    count.isEmpty() ? null : Integer.parseInt(count),
                    totalCount.isEmpty() ? null : Integer.parseInt(totalCount)
            );

            // Assert
            assertTrue(baseRangeCounter.isSingleValueOnly());
        }
    }
}
