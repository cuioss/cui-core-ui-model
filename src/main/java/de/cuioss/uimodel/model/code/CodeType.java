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

import java.io.Serializable;
import java.util.Locale;

/**
 * Defines a unified contract for handling coded values across different system layers.
 * This interface provides a common abstraction for various code implementations
 * (e.g., Code, CodeDto, CodedValueDto, AnnotatedCode), enabling loose coupling
 * between UI and backend components while facilitating testing through simple mock creation.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Unified code handling across system layers</li>
 *   <li>Internationalization support through locale-based resolution</li>
 *   <li>Unique identifier requirement for each code</li>
 *   <li>Serialization support for distributed systems</li>
 *   <li>Flexible implementation allowing various resolving strategies</li>
 * </ul>
 *
 * <p>Design Principles:
 * <ul>
 *   <li>Loose coupling between UI and backend</li>
 *   <li>Separation of concerns through DAO pattern</li>
 *   <li>Implementation-agnostic resolving strategies</li>
 *   <li>Support for caching and optimization</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class StatusCode implements CodeType {
 *     private final String code;
 *     private final Map&lt;Locale, String&gt; translations;
 *
 *     public StatusCode(String code, Map&lt;Locale, String&gt; translations) {
 *         this.code = code;
 *         this.translations = new HashMap&lt;&gt;(translations);
 *     }
 *
 *     &#64;Override
 *     public String getResolved(Locale locale) {
 *         return translations.getOrDefault(locale,
 *             translations.getOrDefault(Locale.ENGLISH, code));
 *     }
 *
 *     &#64;Override
 *     public String getIdentifier() {
 *         return code;
 *     }
 * }
 *
 * // Using the code type
 * CodeType status = new StatusCode("ACTIVE", Map.of(
 *     Locale.ENGLISH, "Active",
 *     Locale.GERMAN, "Aktiv"
 * ));
 *
 * String germanLabel = status.getResolved(Locale.GERMAN); // Returns "Aktiv"
 * String code = status.getIdentifier(); // Returns "ACTIVE"
 * </pre>
 *
 * <p>Implementation Requirements:
 * <ul>
 *   <li>Implementations must be thread-safe</li>
 *   <li>The identifier must be immutable and non-null</li>
 *   <li>Resolution strategies should handle null or invalid locales gracefully</li>
 *   <li>Consider implementing appropriate equals/hashCode based on the identifier</li>
 * </ul>
 *
 * <p><em>Note:</em> Proper usage requires understanding of the DAO (Data Access Object)
 * pattern and its implications for code resolution and caching strategies.
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see java.util.Locale
 * @see java.io.Serializable
 */
public interface CodeType extends Serializable {

    /**
     * Resolves and returns the localized value for this code using the implementation's
     * defined resolution strategy. The strategy may include:
     * <ul>
     *   <li>Direct lookup in a translation map</li>
     *   <li>Database queries</li>
     *   <li>Resource bundle lookups</li>
     *   <li>Fallback chains for missing translations</li>
     *   <li>Caching mechanisms</li>
     * </ul>
     *
     * <p>Implementations should define clear fallback behavior when:
     * <ul>
     *   <li>The requested locale is not available</li>
     *   <li>No translation exists for the code</li>
     *   <li>The resolution process fails</li>
     * </ul>
     *
     * @param locale the target locale for resolution, must not be null
     * @return the resolved value in the specified locale, or null if resolution fails
     * @throws NullPointerException if locale is null
     */
    String getResolved(Locale locale);

    /**
     * Returns the unique identifier for this code. This identifier serves as:
     * <ul>
     *   <li>A primary key for the code</li>
     *   <li>A fallback display value</li>
     *   <li>A reference key for resolution</li>
     * </ul>
     *
     * <p>The identifier must be:
     * <ul>
     *   <li>Non-null</li>
     *   <li>Immutable</li>
     *   <li>Unique within its context</li>
     * </ul>
     *
     * @return the unique identifier for this code, never null
     */
    String getIdentifier();
}
