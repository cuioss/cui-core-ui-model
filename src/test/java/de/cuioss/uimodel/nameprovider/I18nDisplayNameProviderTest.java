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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider.Builder;
import de.cuioss.uimodel.nameprovider.testdata.ConfiguredDataGenerator;
import de.cuioss.uimodel.nameprovider.testdata.DemoTransformationFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests I18nDisplayNameProvider Implementation")
@PropertyReflectionConfig(skip = true)
class I18nDisplayNameProviderTest extends ValueObjectTest<I18nDisplayNameProvider> {

    @Override
    protected I18nDisplayNameProvider anyValueObject() {
        final Map<Locale, String> imported = new HashMap<>();
        imported.put(Locale.FRENCH, "[fr] text");
        imported.put(Locale.FRENCH, "[fr] text");
        return new I18nDisplayNameProvider(imported, null);
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        @Test
        @DisplayName("Should handle invalid parameters")
        void shouldReactOnWrongParameter() {
            // Arrange
            final var builder = createBuilder();

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> builder.addAll(null),
                    "Map must not be null.");
        }

        @Test
        @DisplayName("Should create provider with valid initialization")
        void shouldCreateProviderOnValidInitialization() {
            // Arrange
            final var builder = createBuilder();
            builder.add(Locale.ENGLISH, "[en] text");
            builder.add(Locale.GERMAN, "[de] text");

            // Act
            final var provider = builder.build();

            // Assert - IDisplayNameProvider API usage
            assertEquals("[en] text", provider.getContent().get(Locale.ENGLISH));
            assertEquals("[de] text", provider.getContent().get(Locale.GERMAN));
            assertNull(provider.getContent().get(Locale.GERMANY));

            // Assert - shortcut method usage
            assertEquals("[en] text", provider.lookupTextFor(Locale.ENGLISH));
            assertEquals("[de] text", provider.lookupTextFor(Locale.GERMAN));
            assertNull(provider.lookupTextFor(Locale.GERMANY));
        }

        @Test
        @DisplayName("Should create provider with transformation")
        void shouldCreateProviderWithTransformation() {
            // Arrange
            final var data = new ConfiguredDataGenerator().next();
            final var transformationFunction = new DemoTransformationFunction();

            // Act
            final var provider = createBuilder()
                    .transformAndAddAll(data, transformationFunction)
                    .build();

            // Assert
            assertEquals("[en] text", provider.getContent().get(Locale.ENGLISH));
            assertEquals("[en_GB] text", provider.getContent().get(Locale.UK));
            assertEquals("[de] text", provider.getContent().get(Locale.GERMAN));
            assertEquals("[de_DE] text", provider.getContent().get(Locale.GERMANY));
        }
    }

    @Nested
    @DisplayName("Lookup Tests")
    class LookupTests {

        @Test
        @DisplayName("Should provide fallback lookup")
        void shouldProvideFallbackLookup() {
            // Arrange
            final var builder = createBuilder();
            builder.add(Locale.UK, "[en_GB] text");
            builder.add(Locale.GERMANY, "[de_DE] text");
            final var provider = builder.build();

            // Assert - shortcut method usage
            assertEquals("[de_DE] text", provider.lookupTextFor(Locale.GERMANY));
            assertEquals("[en_GB] text", provider.lookupTextFor(Locale.UK));
            assertNull(provider.lookupTextFor(Locale.ENGLISH));
            assertNull(provider.lookupTextFor(Locale.GERMAN));

            // Assert - shortcut method with fallback
            assertEquals("[en_GB] text", provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH));
            assertEquals("[de_DE] text", provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN));
            assertNull(provider.lookupTextFor(Locale.CHINESE));
        }

        @Test
        @DisplayName("Should provide default label")
        void shouldProvideDefaultLabel() {
            // Arrange
            final var builder = createBuilder();
            builder.add(Locale.UK, "[en_GB] text");
            builder.add(Locale.GERMANY, "[de_DE] text");
            builder.defaultValue("default");
            final var provider = builder.build();

            // Assert
            assertEquals("[en_GB] text", provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH));
            assertEquals("[de_DE] text", provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN));
            assertEquals("default", provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.CHINESE));
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Should throw exception for null locale")
        void shouldThrowExceptionForNullLocale() {
            // Arrange
            final var builder = createBuilder();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> builder.add(null, "[en] text"));
        }

        @Test
        @DisplayName("Should throw exception for null entry in set")
        void shouldThrowExceptionForNullEntry() {
            // Arrange
            final Set<Entry<Locale, String>> set = new HashSet<>();
            set.add(null);
            final var builder = createBuilder();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> builder.add(set));
        }

        @Test
        @DisplayName("Should throw exception for null lookup locale")
        void shouldThrowExceptionForNullLookupLocale() {
            // Arrange
            final Map<Locale, String> imported = new HashMap<>();
            imported.put(Locale.FRENCH, "[fr] text");
            final var provider = new I18nDisplayNameProvider(imported, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> provider.lookupTextFor(null));
        }

        @Test
        @DisplayName("Should throw exception for null fallback lookup locale")
        void shouldThrowExceptionForNullFallbackLookupLocale() {
            // Arrange
            final Map<Locale, String> imported = new HashMap<>();
            imported.put(Locale.FRENCH, "[fr] text");
            final var provider = new I18nDisplayNameProvider(imported, null);

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> provider.lookupTextWithFallbackFirstFittingLanguageOnly(null));
        }

        @Test
        @DisplayName("Should throw exception for null set")
        void shouldThrowExceptionForNullSet() {
            // Arrange
            final var builder = createBuilder();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> builder.add((Set<Entry<Locale, String>>) null));
        }
    }

    private static Builder createBuilder() {
        return new Builder();
    }
}
