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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
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
            assertThat(provider.getContent().get(Locale.ENGLISH), is("[en] text"));
            assertThat(provider.getContent().get(Locale.GERMAN), is("[de] text"));
            assertThat(provider.getContent().get(Locale.GERMANY), is(nullValue()));

            // Assert - shortcut method usage
            assertThat(provider.lookupTextFor(Locale.ENGLISH), is("[en] text"));
            assertThat(provider.lookupTextFor(Locale.GERMAN), is("[de] text"));
            assertThat(provider.lookupTextFor(Locale.GERMANY), is(nullValue()));
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
            assertThat(provider.getContent().get(Locale.ENGLISH), is("[en] text"));
            assertThat(provider.getContent().get(Locale.UK), is("[en_GB] text"));
            assertThat(provider.getContent().get(Locale.GERMAN), is("[de] text"));
            assertThat(provider.getContent().get(Locale.GERMANY), is("[de_DE] text"));
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
            assertThat(provider.lookupTextFor(Locale.GERMANY), is("[de_DE] text"));
            assertThat(provider.lookupTextFor(Locale.UK), is("[en_GB] text"));
            assertThat(provider.lookupTextFor(Locale.ENGLISH), is(nullValue()));
            assertThat(provider.lookupTextFor(Locale.GERMAN), is(nullValue()));

            // Assert - shortcut method with fallback
            assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH), is("[en_GB] text"));
            assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN), is("[de_DE] text"));
            assertThat(provider.lookupTextFor(Locale.CHINESE), is(nullValue()));
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
            assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH), is("[en_GB] text"));
            assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN), is("[de_DE] text"));
            assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.CHINESE), is("default"));
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
