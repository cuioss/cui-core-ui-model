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
package de.cuioss.uimodel.model.conceptkey;

import de.cuioss.uimodel.model.code.CodeType;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Extends {@link CodeType} to provide a rich, immutable representation of concept keys
 * with additional metadata support. This interface defines a type-safe enumeration pattern
 * for handling concept-based data structures, particularly in medical and healthcare
 * contexts.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Category-based organization via {@link ConceptCategory}</li>
 *   <li>Alias support for code mapping</li>
 *   <li>Immutable key-value metadata storage</li>
 *   <li>Natural ordering support</li>
 *   <li>Type-safe enumeration pattern</li>
 * </ul>
 *
 * <p>Design Principles:
 * <ul>
 *   <li>Immutability: All implementations must be immutable</li>
 *   <li>Type Safety: Implements type-safe enumeration pattern</li>
 *   <li>Metadata Support: Structured storage of additional information</li>
 *   <li>Extensibility: Allows for category-specific implementations</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class DiagnosisKey implements ConceptKeyType {
 *     private final String code;
 *     private final Map&lt;String, String&gt; metadata;
 *     private final Set&lt;String&gt; aliases;
 *
 *     public DiagnosisKey(String code, Map&lt;String, String&gt; metadata,
 *             Set&lt;String&gt; aliases) {
 *         this.code = requireNonNull(code);
 *         this.metadata = new HashMap&lt;&gt;(metadata);
 *         this.aliases = new HashSet&lt;&gt;(aliases);
 *     }
 *
 *     &#64;Override
 *     public ConceptCategory getCategory() {
 *         return ConceptCategory.DIAGNOSIS;
 *     }
 *
 *     &#64;Override
 *     public Set&lt;String&gt; getAliases() {
 *         return Collections.unmodifiableSet(aliases);
 *     }
 *
 *     &#64;Override
 *     public String get(String key, String defaultValue) {
 *         return metadata.getOrDefault(key, defaultValue);
 *     }
 *
 *     // ... other implementation methods
 * }
 * </pre>
 *
 * <p>Implementation Requirements:
 * <ul>
 *   <li>Must be immutable</li>
 *   <li>Must handle null values appropriately</li>
 *   <li>Should implement proper equals/hashCode</li>
 *   <li>Should provide meaningful toString</li>
 *   <li>Must maintain thread-safety</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CodeType
 * @see ConceptCategory
 * @see Comparable
 * @see Serializable
 */
public interface ConceptKeyType extends CodeType, Comparable<ConceptKeyType> {

    /**
     * Returns the category to which this concept key belongs. The category
     * defines the semantic context and available operations for this concept.
     *
     * <p>Categories help in:
     * <ul>
     *   <li>Organizing concepts by domain</li>
     *   <li>Determining available operations</li>
     *   <li>Validating relationships</li>
     *   <li>Filtering and grouping</li>
     * </ul>
     *
     * @return the category of this concept, never null
     * @see ConceptCategory
     */
    ConceptCategory getCategory();

    /**
     * Returns the set of alternative identifiers (aliases) for this concept.
     * Aliases enable mapping between different coding systems or historical
     * identifiers for the same concept.
     *
     * <p>Common uses include:
     * <ul>
     *   <li>Legacy system compatibility</li>
     *   <li>Cross-system mapping</li>
     *   <li>Alternative code support</li>
     *   <li>Version compatibility</li>
     * </ul>
     *
     * @return an unmodifiable set of aliases, never null but may be empty
     */
    Set<String> getAliases();

    /**
     * Retrieves a metadata value for the specified key, with a fallback default value.
     * This method provides safe access to the concept's metadata with graceful
     * handling of missing values.
     *
     * @param key identifying the content, must not be null or empty
     * @param defaultValue to return if no value exists for the key
     * @return the value associated with the key, or defaultValue if none exists
     * @throws IllegalArgumentException if key is null or empty
     */
    String get(String key, String defaultValue);

    /**
     * Retrieves a metadata value for the specified key. This is a convenience
     * method equivalent to calling {@link #get(String, String)} with null as
     * the default value.
     *
     * @param key identifying the content, must not be null or empty
     * @return the value associated with the key, or null if none exists
     * @throws IllegalArgumentException if key is null or empty
     */
    String get(String key);

    /**
     * Checks if this concept contains metadata for the specified key.
     *
     * @param key identifying the content, must not be null or empty
     * @return true if the concept contains metadata for the key, false otherwise
     * @throws IllegalArgumentException if key is null or empty
     */
    boolean containsKey(String key);

    /**
     * Provides access to all metadata entries associated with this concept.
     * The returned set is unmodifiable to maintain immutability.
     *
     * <p>The entries can be used for:
     * <ul>
     *   <li>Metadata iteration</li>
     *   <li>Bulk processing</li>
     *   <li>Data export</li>
     *   <li>Debugging</li>
     * </ul>
     *
     * @return an unmodifiable set of metadata entries, never null but may be empty
     */
    Set<Map.Entry<String, String>> entrySet();
}
