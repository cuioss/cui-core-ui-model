package de.cuioss.uimodel.nameprovider;

import static de.cuioss.tools.base.Preconditions.checkArgument;
import static de.cuioss.tools.collect.CollectionLiterals.mutableSet;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Attention : stored translated text is <b>NOT</b> sanitized, on usage you need to care about!
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class I18nDisplayNameProvider implements IDisplayNameProvider<Map<Locale, String>> {

    private static final String LOCALE_MUST_NOT_BE_NULL = "Locale must not be null.";

    private static final long serialVersionUID = 416489243142980911L;

    @Getter
    private final Map<Locale, String> content;

    protected final String defaultValue;

    /**
     * @param defaultValue to be set
     */
    public I18nDisplayNameProvider(String defaultValue) {
        content = new HashMap<>();
        this.defaultValue = defaultValue;
    }

    /**
     * Shortcut method provide lookup to localized text in storage
     *
     * @param locale {@linkplain Locale} must not be {@code null}
     *
     * @return corresponding localized text if exists, {@code null} otherwise
     * @throws IllegalArgumentException if locale is {@code null}
     */
    public String lookupTextFor(final Locale locale) {

        checkArgument(null != locale, LOCALE_MUST_NOT_BE_NULL);

        return content.get(locale);

    }

    /**
     * Shortcut method provide lookup to localized text in storage with fall-back strategy where
     * only
     * language is required for match
     *
     * @param locale {@linkplain Locale} must not be {@code null}
     *
     * @return corresponding localized text if exists, {@code null} otherwise
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
     * Builder for creation in safe way a {@linkplain I18nDisplayNameProvider}
     */
    public static class Builder {

        private final Map<Locale, String> collectedData;

        private String defaultValue;

        /**
         * Default constructor
         */
        public Builder() {
            collectedData = new HashMap<>();
        }

        /**
         * Copy constructor
         *
         * @param copyFrom
         */
        public Builder(I18nDisplayNameProvider copyFrom) {
            collectedData = new HashMap<>(copyFrom.content);
            defaultValue = copyFrom.defaultValue;
        }

        /**
         * Store all entries which should be used for creation.
         * Attention on add entries each will get checked!
         *
         * @param data {@linkplain Map} must not be null
         *
         * @return {@linkplain Builder} in fluent api style
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
         * @param locale {@linkplain Locale} must not be {@code null}
         * @param text any String
         *
         * @return {@linkplain Builder} in fluent api style
         * @throws IllegalArgumentException if locale is {@code null}
         * @throws IllegalArgumentException if a entry for locale already exists
         */
        public Builder add(final Locale locale, final String text) {

            checkArgument(null != locale, LOCALE_MUST_NOT_BE_NULL);

            collectedData.put(locale, text);

            return this;
        }

        /**
         * Store all entries which should be used for creation.
         * Attention on add entries each will get checked!
         *
         * @param entries {@linkplain Set} of enties must not be {@code null}
         *
         * @return {@linkplain Builder} in fluent api style
         * @throws IllegalArgumentException if parameter entries is {@code null}
         * @throws IllegalArgumentException if locale is of entry is {@code null}
         * @throws IllegalArgumentException if a entry for locale already exists
         */
        public Builder add(Set<Entry<Locale, String>> entries) {

            checkArgument(null != entries, "Entry must not be null.");

            for (final Entry<Locale, String> entry : entries) {
                this.add(entry);
            }

            return this;
        }

        /**
         * @param entry {@linkplain Entry} must not be {@code null}
         *
         * @return {@linkplain Builder} in fluent api style
         * @throws IllegalArgumentException if locale is of entry is {@code null}
         * @throws IllegalArgumentException if a entry for locale already exists
         */
        public Builder add(Entry<Locale, String> entry) {

            checkArgument(null != entry, "Entry must not be null.");
            checkArgument(null != entry.getKey(), LOCALE_MUST_NOT_BE_NULL);

            collectedData.put(entry.getKey(), entry.getValue());

            return this;
        }

        /**
         * Transform and add all data by using {@linkplain Function} to the builder
         *
         * @param data must not be null
         * @param transormationFunction must not be null
         *
         * @return {@linkplain Builder} in fluent api style
         * @throws IllegalArgumentException if data is {@code null}
         * @throws IllegalArgumentException if function is {@code null}
         */
        public <T> Builder transformAndAddAll(final Stream<T> data,
                Function<T, Entry<Locale, String>> transormationFunction) {

            checkArgument(null != data, "Data must not be null.");
            checkArgument(null != transormationFunction, "Function must not be null.");

            this.add(mutableSet(data.map(transormationFunction)));

            return this;
        }

        /**
         * @param defaultValue to be set
         * @return {@linkplain Builder} in fluent api style
         */
        public Builder defaultValue(final String defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        /**
         * @return initialized {@linkplain I18nDisplayNameProvider}
         */
        public I18nDisplayNameProvider build() {
            return new I18nDisplayNameProvider(collectedData, defaultValue);
        }

    }

}
