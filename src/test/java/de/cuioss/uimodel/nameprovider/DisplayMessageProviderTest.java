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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.generator.Generators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests DisplayMessageProvider Implementation")
class DisplayMessageProviderTest {

    private DisplayMessageProvider target;

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should verify mandatory parameter")
        void shouldVerifyMandatoryParameter() {
            // Act & Assert
            assertThrows(NullPointerException.class, () -> new DisplayMessageProvider(null));
        }

        @Test
        @DisplayName("Should provide content")
        void shouldProvideContent() {
            // Arrange
            final var displayMessageFormat = anyDisplayMessageFormat();

            // Act
            target = new DisplayMessageProvider(displayMessageFormat);

            // Assert
            assertEquals(displayMessageFormat, target.getContent());
        }
    }

    @Nested
    @DisplayName("Message Resolution Tests")
    class MessageResolutionTests {

        private MockResourceBundle bundle;

        @BeforeEach
        void setUp() {
            bundle = new MockResourceBundle();
        }

        @Test
        @DisplayName("Should resolve message with parameter")
        void shouldProvideResolving() {
            // Arrange
            final var key = "some.error.key";
            final var value = "Error occurs on {0}";
            bundle.add(key, value);

            final var displayMessageFormat = new DisplayMessageFormat(key, "optional service");
            target = new DisplayMessageProvider(displayMessageFormat);

            // Act & Assert
            assertEquals("Error occurs on optional service", target.getMessageFormated(bundle));
        }
    }

    private static DisplayMessageFormat anyDisplayMessageFormat() {
        return new DisplayMessageFormat(
                Generators.letterStrings(1, 10).next(),
                Generators.letterStrings(1, 10).next()
        );
    }

    private static class MockResourceBundle extends ResourceBundle {

        private final Map<String, Object> storage;

        public MockResourceBundle() {
            storage = new HashMap<>();
        }

        public MockResourceBundle add(final String key, final Object value) {
            storage.put(key, value);
            return this;
        }

        @Override
        protected Object handleGetObject(final String key) {
            if (storage.containsKey(key)) {
                return storage.get(key);
            }
            throw new MissingResourceException("Unknown key", "MockResourceBundle", key);
        }

        @Override
        public Enumeration<String> getKeys() {
            return null;
        }
    }
}
