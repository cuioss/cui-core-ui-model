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
package de.cuioss.uimodel.model.code;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * A simple, immutable implementation of {@link CodeType} that provides a locale-independent
 * resolution strategy. This implementation always returns a predefined string regardless
 * of the provided locale, making it suitable for cases where internationalization
 * is not required or handled elsewhere.
 *
 * <p>Features:
 * <ul>
 *   <li>Immutable implementation</li>
 *   <li>Null-safe construction</li>
 *   <li>Locale-independent resolution</li>
 *   <li>Proper equals/hashCode implementation</li>
 *   <li>Useful toString representation</li>
 *   <li>Serialization support</li>
 * </ul>
 *
 * <p>This implementation is particularly useful for:
 * <ul>
 *   <li>Testing and mocking</li>
 *   <li>Static, non-localized codes</li>
 *   <li>Default implementations</li>
 *   <li>Prototype development</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Basic usage with same value for resolved and identifier
 * CodeType simple = new CodeTypeImpl("ACTIVE");
 *
 * // Different values for display and identification
 * CodeType status = new CodeTypeImpl("Active Status", "STATUS_ACTIVE");
 *
 * // The resolved value is locale-independent
 * String value = status.getResolved(Locale.GERMAN); // Returns "Active Status"
 * String id = status.getIdentifier(); // Returns "STATUS_ACTIVE"
 * </pre>
 *
 * <p>Thread Safety:
 * This class is immutable and thus thread-safe. All instance fields are final
 * and the class provides no means of modification after construction.
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CodeType
 * @see java.io.Serializable
 */
@EqualsAndHashCode
@ToString
public class CodeTypeImpl implements CodeType {

    @Serial
    private static final long serialVersionUID = 6827791409255699288L;

    /** The resolved value that will be returned regardless of locale. */
    private final String resolved;

    /** The unique identifier for this code type. */
    @Getter
    private final String identifier;

    /**
     * Constructs a new CodeTypeImpl with different values for resolution and identification.
     *
     * <p>This constructor is useful when you need to maintain a technical identifier
     * that differs from the display value. For example:
     * <pre>
     * new CodeTypeImpl("Active User", "USER_ACTIVE")
     * </pre>
     *
     * @param resolved the string to be returned by {@link #getResolved(Locale)},
     *                regardless of the locale parameter
     * @param identifier the unique identifier for this code type
     * @throws NullPointerException if either parameter is null
     */
    public CodeTypeImpl(final String resolved, final String identifier) {
        this.resolved = requireNonNull(resolved, "resolved must not be null");
        this.identifier = requireNonNull(identifier, "identifier must not be null");
    }

    /**
     * Convenience constructor that uses the same value for both the resolved
     * string and identifier. This is useful when the display value and the
     * identifier are the same.
     *
     * <p>Example:
     * <pre>
     * new CodeTypeImpl("ACTIVE") // Both resolved and identifier will be "ACTIVE"
     * </pre>
     *
     * @param resolved the string to be used as both the resolved value and identifier
     * @throws NullPointerException if resolved is null
     */
    public CodeTypeImpl(final String resolved) {
        this(resolved, resolved);
    }

    /**
     * {@inheritDoc}
     *
     * <p>This implementation ignores the locale parameter and always returns the
     * value provided at construction time. This makes it suitable for:
     * <ul>
     *   <li>Non-localized content</li>
     *   <li>Pre-localized content</li>
     *   <li>Technical identifiers</li>
     *   <li>Testing scenarios</li>
     * </ul>
     *
     * @param locale ignored by this implementation
     * @return the resolved string provided at construction time
     */
    @Override
    public String getResolved(final Locale locale) {
        return resolved;
    }
}
