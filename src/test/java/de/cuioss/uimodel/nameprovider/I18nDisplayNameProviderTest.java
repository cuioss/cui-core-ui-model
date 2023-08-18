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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider.Builder;
import de.cuioss.uimodel.nameprovider.testdata.ConfiguredDataGenerator;
import de.cuioss.uimodel.nameprovider.testdata.DemoTransformationFunction;

@PropertyReflectionConfig(skip = true)
class I18nDisplayNameProviderTest extends ValueObjectTest<I18nDisplayNameProvider> {

    @Override
    protected I18nDisplayNameProvider anyValueObject() {
        final Map<Locale, String> imported = new HashMap<>();
        imported.put(Locale.FRENCH, "[fr] text");
        imported.put(Locale.FRENCH, "[fr] text");
        return new I18nDisplayNameProvider(imported, null);
    }

    @Test
    void shouldReactOnWrongParameter() {

        final var builder = createBuilder();

        // map must not be null
        try {
            builder.addAll(null);
            fail();
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("Map must not be null."));
        }
    }

    @Test
    void shouldCreateProviderOnValidInitialization() {
        final var builder = createBuilder();

        builder.add(Locale.ENGLISH, "[en] text");
        builder.add(Locale.GERMAN, "[de] text");

        final var provider = builder.build();

        // IDisplayNameProvider api usage
        assertThat(provider.getContent().get(Locale.ENGLISH), is("[en] text"));
        assertThat(provider.getContent().get(Locale.GERMAN), is("[de] text"));
        assertThat(provider.getContent().get(Locale.GERMANY), is(nullValue()));

        // short cut method usage
        assertThat(provider.lookupTextFor(Locale.ENGLISH), is("[en] text"));
        assertThat(provider.lookupTextFor(Locale.GERMAN), is("[de] text"));
        assertThat(provider.lookupTextFor(Locale.GERMANY), is(nullValue()));
    }

    @Test
    void shouldCreateProviderWithTransformation() {
        final var data = new ConfiguredDataGenerator().next();
        final var transormationFunction = new DemoTransformationFunction();

        final var provider = createBuilder().transformAndAddAll(data, transormationFunction).build();

        assertThat(provider.getContent().get(Locale.ENGLISH), is("[en] text"));
        assertThat(provider.getContent().get(Locale.UK), is("[en_GB] text"));
        assertThat(provider.getContent().get(Locale.GERMAN), is("[de] text"));
        assertThat(provider.getContent().get(Locale.GERMANY), is("[de_DE] text"));
    }

    @Test
    void shouldProvideFallbackLookup() {
        final var builder = createBuilder();

        builder.add(Locale.UK, "[en_GB] text");
        builder.add(Locale.GERMANY, "[de_DE] text");

        final var provider = builder.build();

        // short cut method usage
        assertThat(provider.lookupTextFor(Locale.GERMANY), is("[de_DE] text"));
        assertThat(provider.lookupTextFor(Locale.UK), is("[en_GB] text"));

        assertThat(provider.lookupTextFor(Locale.ENGLISH), is(nullValue()));
        assertThat(provider.lookupTextFor(Locale.GERMAN), is(nullValue()));

        // short cut method with fallback
        assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH), is("[en_GB] text"));
        assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN), is("[de_DE] text"));
        assertThat(provider.lookupTextFor(Locale.CHINESE), is(nullValue()));
    }

    @Test
    void shouldProvideDefaultLabel() {
        final var builder = createBuilder();

        builder.add(Locale.UK, "[en_GB] text");
        builder.add(Locale.GERMANY, "[de_DE] text");
        builder.defaultValue("default");

        final var provider = builder.build();

        // short cut method with fallback
        assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.ENGLISH), is("[en_GB] text"));
        assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.GERMAN), is("[de_DE] text"));
        assertThat(provider.lookupTextWithFallbackFirstFittingLanguageOnly(Locale.CHINESE), is("default"));
    }

    @Test
    void shouldThrowException() {
        final var builder = createBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.add(null, "[en] text"));
    }

    @Test
    void shouldThrowException2() {
        final Set<Entry<Locale, String>> set = new HashSet<>();
        set.add(null);
        final var builder = createBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.add(set));
    }

    @Test
    void shouldThrowException3() {
        final Map<Locale, String> imported = new HashMap<>();
        imported.put(Locale.FRENCH, "[fr] text");
        imported.put(Locale.FRENCH, "[fr] text");
        final var displayNameProvider = new I18nDisplayNameProvider(imported, null);
        assertThrows(IllegalArgumentException.class, () -> displayNameProvider.lookupTextFor(null));

    }

    @Test
    void shouldThrowException4() {

        final Map<Locale, String> imported = new HashMap<>();
        imported.put(Locale.FRENCH, "[fr] text");
        imported.put(Locale.FRENCH, "[fr] text");
        final var displayNameProvider = new I18nDisplayNameProvider(imported, null);
        assertThrows(IllegalArgumentException.class,
                () -> displayNameProvider.lookupTextWithFallbackFirstFittingLanguageOnly(null));

    }

    @Test
    void shouldThrowException6() {

        final Set<Entry<Locale, String>> set = null;
        final var builder = createBuilder();
        assertThrows(IllegalArgumentException.class, () -> builder.add(set));

    }

    private static Builder createBuilder() {
        return new Builder();
    }
}
