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
package de.cuioss.uimodel.model;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import static de.cuioss.test.generator.Generators.letterStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableGeneratorController
@DisplayName("Tests Gender Enumeration")
class GenderTest {

    private static final Collection<Entry<String, Gender>> TEST_VALUES = initTestValues();

    @Nested
    @DisplayName("String to Gender Conversion Tests")
    class ConversionTests {

        @Test
        @DisplayName("Should convert valid strings to Gender")
        final void shouldDetermineEnumFromString() {
            // Arrange
            final var testItem = anyTestItem();
            final var genderString = testItem.getKey();
            final var expected = testItem.getValue();

            // Act & Assert
            assertEquals(expected, Gender.fromString(genderString));
        }

        @Test
        @DisplayName("Should handle undefined values")
        final void shouldHandleUndefinedValues() {
            // Arrange
            final var expected = Gender.UNKNOWN;

            // Act & Assert
            assertEquals(expected, Gender.fromString(null));
            assertEquals(expected, Gender.fromString(""));
            assertEquals(expected, Gender.fromString(letterStrings(2, 10).next()));
        }
    }

    @Nested
    @DisplayName("Label Key Tests")
    class LabelKeyTests {

        @Test
        @DisplayName("Should provide message key for all values")
        final void shouldProvideMsgKey() {
            // Act & Assert
            for (final Gender entry : Gender.values()) {
                assertNotNull(entry.getLabelKey(), entry + " has no valid title info");
            }
        }

        @Test
        @DisplayName("Should provide correct label key")
        final void shouldBeLabelKeyProvider() {
            // Act & Assert
            assertEquals("cui.model.gender.male.title", Gender.MALE.getLabelKey());
        }
    }

    private static Entry<String, Gender> anyTestItem() {
        return Generators.fixedValues(TEST_VALUES).next();
    }

    private static Collection<Entry<String, Gender>> initTestValues() {
        final Set<Entry<String, Gender>> result = new HashSet<>();
        result.add(new TestEntry("m", Gender.MALE));
        result.add(new TestEntry("male", Gender.MALE));
        result.add(new TestEntry("f", Gender.FEMALE));
        result.add(new TestEntry("female", Gender.FEMALE));
        result.add(new TestEntry("o", Gender.OTHER));
        result.add(new TestEntry("other", Gender.OTHER));
        result.add(new TestEntry("d", Gender.DIVERSE));
        result.add(new TestEntry("diverse", Gender.DIVERSE));
        result.add(new TestEntry("x", Gender.UNDEFINED));
        result.add(new TestEntry("undefined", Gender.UNDEFINED));
        return result;
    }

    @AllArgsConstructor
    private static class TestEntry implements java.util.Map.Entry<String, Gender> {

        private final String key;
        private final Gender value;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Gender getValue() {
            return value;
        }

        @Override
        public Gender setValue(Gender value) {
            throw new UnsupportedOperationException();
        }
    }
}
