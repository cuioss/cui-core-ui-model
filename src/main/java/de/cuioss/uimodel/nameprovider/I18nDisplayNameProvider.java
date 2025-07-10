/**
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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

/**
 * An implementation of {@link IDisplayNameProvider} that provides internationalization
 * (i18n) support through a map of locale-specific text values. This class enables
 * flexible handling of translated content with fallback mechanisms.
 *
 * <p><strong>Security Note:</strong> The stored translated text is <b>NOT</b>
 * sanitized. It is the responsibility of the consumer to handle proper text
 * sanitization before display.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Locale-based text storage</li>
 *   <li>Fallback mechanism for language matches</li>
 *   <li>Builder pattern for safe construction</li>
 *   <li>Default value support</li>
 *   <li>Flexible text lookup strategies</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Simple construction with default value
 * I18nDisplayNameProvider provider = new I18nDisplayNameProvider("Default");
 *
 * // Using builder pattern
 * I18nDisplayNameProvider provider = new I18nDisplayNameProvider.Builder()
 *     .add(Locale.ENGLISH, "Hello")
 *     .add(Locale.GERMAN, "Hallo")
 *     .defaultValue("Hello")
 *     .build();
 *
 * // Looking up text
 * String text = provider.lookupTextFor(Locale.ENGLISH); // Returns "Hello"
 * String fallback = provider.lookupTextWithFallbackFirstFittingLanguageOnly(
 *     Locale.US); // Returns "Hello" (language match)
 *
 * // Copying and extending
 * I18nDisplayNameProvider copy = new I18nDisplayNameProvider.Builder(provider)
 *     .add(Locale.FRENCH, "Bonjour")
 *     .build();
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Thread-safe for reading operations</li>
 *   <li>Immutable after construction</li>
 *   <li>Null-safe operations with proper validation</li>
 *   <li>Efficient locale-based lookup</li>
 *   <li>Support for language-only fallback</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Multi-language UI labels</li>
 *   <li>Localized error messages</li>
 *   <li>Dynamic content translation</li>
 *   <li>Region-specific displays</li>
 *   <li>Fallback text handling</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see Locale
 * @see IDisplayNameProvider
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class I18nDisplayNameProvider implements IDisplayNameProvider<Map<Locale, String>> {

    private static final String LOCALE_MUST_NOT_BE_NULL = "Locale must not be null.";

    @Serial
    private static final long serialVersionUID = 416489243142980911L;

    /**
     * The map containing locale-specific text values.
     * Keys are {@link Locale} objects and values are the corresponding translations.
     */
    @Getter
    private final Map<Locale, String> content;

    /**
     * The default value to return when no matching translation is found.
     */
    protected final String defaultValue;

    /**
     * Creates a new provider with an empty content map and the specified default value.
     *
     * @param defaultValue the value to return when no translation is found
     */
    public I18nDisplayNameProvider(String defaultValue) {
        content = new HashMap<>();
        this.defaultValue = defaultValue;
    }

    /**
     * Looks up the text for the specified locale. This method performs an exact
     * locale match without any fallback mechanism.
     *
     * @param locale the locale to look up, must not be {@code null}
     * @return the corresponding localized text if it exists, {@code null} otherwise
     * @throws IllegalArgumentException if locale is {@code null}
     */
    public String lookupTextFor(final Locale locale) {
        checkArgument(null != locale, LOCALE_MUST_NOT_BE_NULL);
        return content.get(locale);
    }

    /**
     * Looks up the text for the specified locale with a language-only fallback
     * strategy. This method will:
     * <ol>
     *   <li>Try to find an exact locale match</li>
     *   <li>If not found, look for any locale with the same language</li>
     *   <li>If still not found, return the default value</li>
     * </ol>
     *
     * @param locale the locale to look up, must not be {@code null}
     * @return the corresponding localized text, or default value if no match found
     * @throws IllegalArgumentException if locale is {@code null}
     */
    public String lookupTextWithFallbackFirstFittingLanguageOnly(final Locale locale) {
        checkArgument(null != locale, LOCALE_MUST_NOT_BE_NULL);

        for (final Entry<Locale, String> entry : content.entrySet()) {
            if (entry.getKey().getLanguage().equals(locale.getLanguage())) {
                return entry.getValue();
            }
        }

        return defaultValue;
    }

    /**
     * Builder for creating {@link I18nDisplayNameProvider} instances in a safe
     * and flexible manner. The builder ensures proper validation of all inputs
     * and provides a fluent API for construction.
     *
     * <p>Features:
     * <ul>
     *   <li>Fluent API design</li>
     *   <li>Input validation</li>
     *   <li>Flexible data addition</li>
     *   <li>Copy construction support</li>
     *   <li>Stream transformation support</li>
     * </ul>
     */
    public static class Builder {

        private final Map<Locale, String> collectedData;
        private String defaultValue;

        /**
         * Creates a new builder with an empty data collection.
         */
        public Builder() {
            collectedData = new HashMap<>();
        }

        /**
         * Creates a new builder initialized with data from an existing provider.
         *
         * @param copyFrom the provider to copy data from
         */
        public Builder(I18nDisplayNameProvider copyFrom) {
            collectedData = new HashMap<>(copyFrom.content);
            defaultValue = copyFrom.defaultValue;
        }

        /**
         * Adds all entries from the provided map to this builder.
         * Each entry is validated before being added.
         *
         * @param data the map of locale-text pairs to add, must not be null
         * @return this builder for method chaining
         * @throws IllegalArgumentException if data is {@code null}
         */
        public Builder addAll(final Map<Locale, String> data) {
            checkArgument(null != data, "Map must not be null.");
            for (final Entry<Locale, String> entry : data.entrySet()) {
                this.add(entry.getKey(), entry.getValue());
            }
            return this;
        }

        /**
         * Adds a single locale-text pair to this builder.
         *
         * @param locale the locale for the text, must not be null
         * @param text the text to associate with the locale
         * @return this builder for method chaining
         * @throws IllegalArgumentException if locale is null
         */
        public Builder add(final Locale locale, final String text) {
            checkArgument(null != locale, LOCALE_MUST_NOT_BE_NULL);
            collectedData.put(locale, text);
            return this;
        }

        /**
         * Adds multiple locale-text pairs from a set of entries.
         *
         * @param entries the set of entries to add, must not be null
         * @return this builder for method chaining
         * @throws IllegalArgumentException if entries is null or contains null locale
         */
        public Builder add(Set<Entry<Locale, String>> entries) {
            checkArgument(null != entries, "Entry must not be null.");
            for (final Entry<Locale, String> entry : entries) {
                this.add(entry);
            }
            return this;
        }

        /**
         * Adds a single entry containing a locale-text pair.
         *
         * @param entry the entry to add, must not be null and must have non-null locale
         * @return this builder for method chaining
         * @throws IllegalArgumentException if entry or its locale is null
         */
        public Builder add(Entry<Locale, String> entry) {
            checkArgument(null != entry, "Entry must not be null.");
            checkArgument(null != entry.getKey(), LOCALE_MUST_NOT_BE_NULL);
            collectedData.put(entry.getKey(), entry.getValue());
            return this;
        }

        /**
         * Transforms and adds data from a stream using the provided transformation function.
         *
         * @param <T> the type of data in the stream
         * @param data the stream of data to transform and add
         * @param transormationFunction the function to transform data to locale-text entries
         * @return this builder for method chaining
         * @throws IllegalArgumentException if data or function is null
         */
        public <T> Builder transformAndAddAll(final Stream<T> data,
                Function<T, Entry<Locale, String>> transormationFunction) {
            checkArgument(null != data, "Data must not be null.");
            checkArgument(null != transormationFunction, "Function must not be null.");
            this.add(mutableSet(data.map(transormationFunction)));
            return this;
        }

        /**
         * Sets the default value to return when no translation is found.
         *
         * @param defaultValue the default value to use
         * @return this builder for method chaining
         */
        public Builder defaultValue(final String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * Builds and returns a new {@link I18nDisplayNameProvider} with the
         * collected data and settings.
         *
         * @return a new I18nDisplayNameProvider instance
         */
        public I18nDisplayNameProvider build() {
            return new I18nDisplayNameProvider(collectedData, defaultValue);
        }
    }
}
