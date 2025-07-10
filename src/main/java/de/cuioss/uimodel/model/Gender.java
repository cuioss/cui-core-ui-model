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
package de.cuioss.uimodel.model;

import de.cuioss.uimodel.nameprovider.LabelKeyProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

/**
 * Represents gender identities in the system, providing both visual (CSS icon classes)
 * and localization support. This enumeration is designed to be inclusive and
 * support modern gender identity requirements while maintaining compatibility
 * with legacy systems.
 *
 * <p>Features:
 * <ul>
 *   <li>Comprehensive gender identity support (Male, Female, Other, Diverse, etc.)</li>
 *   <li>Built-in icon CSS classes for visual representation</li>
 *   <li>Internationalization support through label keys</li>
 *   <li>Case-insensitive string parsing with common abbreviations</li>
 *   <li>Fail-safe factory method defaulting to UNKNOWN</li>
 * </ul>
 *
 * <p>Supported Values:
 * <ul>
 *   <li>{@link #MALE} - Male gender identity (m, male)</li>
 *   <li>{@link #FEMALE} - Female gender identity (f, female)</li>
 *   <li>{@link #OTHER} - Other gender identity (o, other)</li>
 *   <li>{@link #DIVERSE} - Diverse gender identity (d, diverse)</li>
 *   <li>{@link #UNDEFINED} - Undefined gender (x, undefined)</li>
 *   <li>{@link #UNKNOWN} - Unknown or not specified</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Parse from string with fallback
 * Gender gender = Gender.fromString("m");        // Returns MALE
 * Gender unknown = Gender.fromString(null);      // Returns UNKNOWN
 *
 * // Access icon and label information
 * String cssClass = gender.getCssClass();        // Returns "cui-icon-gender_male"
 * String labelKey = gender.getLabelKey();        // Returns "cui.model.gender.male.title"
 *
 * // Use in UI components
 * &#64;Inject
 * private Messages messages;
 *
 * public String getGenderLabel(Gender gender) {
 *     return messages.getString(gender.getLabelKey());
 * }
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>The enum implements {@link LabelKeyProvider} for i18n support</li>
 *   <li>All string parsing is case-insensitive for flexibility</li>
 *   <li>The mapping is immutable and thread-safe</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender implements LabelKeyProvider {

    /**
     * Represents male gender identity.
     * CSS Class: cui-icon-gender_male
     * Label Key: cui.model.gender.male.title
     * String mappings: "m", "male"
     */
    MALE("cui-icon-gender_male", "cui.model.gender.male.title"),

    /**
     * Represents female gender identity.
     * CSS Class: cui-icon-gender_female
     * Label Key: cui.model.gender.female.title
     * String mappings: "f", "female"
     */
    FEMALE("cui-icon-gender_female", "cui.model.gender.female.title"),

    /**
     * Represents other gender identities.
     * CSS Class: cui-icon-gender_other
     * Label Key: cui.model.gender.other.title
     * String mappings: "o", "other"
     */
    OTHER("cui-icon-gender_other", "cui.model.gender.other.title"),

    /**
     * Represents diverse gender identity.
     * CSS Class: cui-icon-gender_divers
     * Label Key: cui.model.gender.diverse.title
     * String mappings: "d", "diverse"
     */
    DIVERSE("cui-icon-gender_divers", "cui.model.gender.diverse.title"),

    /**
     * Represents explicitly undefined gender.
     * CSS Class: cui-icon-gender_undefined
     * Label Key: cui.model.gender.undefined.title
     * String mappings: "x", "undefined"
     */
    UNDEFINED("cui-icon-gender_undefined", "cui.model.gender.undefined.title"),

    /**
     * Represents unknown or unspecified gender.
     * CSS Class: cui-icon-gender_unknown
     * Label Key: cui.model.gender.unknown.title
     * Used as default when parsing fails
     */
    UNKNOWN("cui-icon-gender_unknown", "cui.model.gender.unknown.title");

    /**
     * The CSS class name for the gender icon.
     * This class should be defined in your CSS/theme files.
     */
    @Getter
    private final String cssClass;

    /**
     * The resource bundle key for retrieving the localized label.
     * Used with {@link LabelKeyProvider} for i18n support.
     */
    @Getter
    private final String labelKey;

    /** Lookup map for efficient string to enum conversion. */
    private static final Map<String, Gender> MAPPING = initMapping();

    /**
     * Initializes the mapping between string representations and Gender enums.
     * This includes both full names and common abbreviations.
     *
     * @return An immutable map of string keys to Gender values
     */
    private static Map<String, Gender> initMapping() {
        final Map<String, Gender> result = HashMap.newHashMap(6);
        result.put("m", MALE);
        result.put("male", MALE);
        result.put("f", FEMALE);
        result.put("female", FEMALE);
        result.put("o", OTHER);
        result.put("other", OTHER);
        result.put("d", DIVERSE);
        result.put("diverse", DIVERSE);
        result.put("x", UNDEFINED);
        result.put("undefined", UNDEFINED);
        return result;
    }

    /**
     * Converts a string representation to its corresponding Gender enum value.
     * The conversion is case-insensitive and supports both full names and
     * common abbreviations.
     *
     * <p>Conversion Rules:
     * <ul>
     *   <li>null or empty string → {@link #UNKNOWN}</li>
     *   <li>"m" or "male" → {@link #MALE}</li>
     *   <li>"f" or "female" → {@link #FEMALE}</li>
     *   <li>"o" or "other" → {@link #OTHER}</li>
     *   <li>"d" or "diverse" → {@link #DIVERSE}</li>
     *   <li>"x" or "undefined" → {@link #UNDEFINED}</li>
     *   <li>Any other value → {@link #UNKNOWN}</li>
     * </ul>
     *
     * @param value The string to convert, may be null
     * @return The corresponding Gender enum value, never null
     */
    public static Gender fromString(final String value) {
        final var key = nullToEmpty(value).toLowerCase();
        return MAPPING.getOrDefault(key, UNKNOWN);
    }
}
