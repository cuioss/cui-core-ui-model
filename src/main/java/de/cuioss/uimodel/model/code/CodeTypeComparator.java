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
package de.cuioss.uimodel.model.code;

import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

/**
 * A serializable comparator for {@link CodeType} instances that compares them based
 * on their resolved values in a specified locale. This comparator provides a consistent
 * ordering for code types, particularly useful for sorting and organizing codes in
 * user interfaces.
 *
 * <p>Features:
 * <ul>
 *   <li>Locale-aware comparison</li>
 *   <li>Null-safe operation</li>
 *   <li>Serializable for distributed systems</li>
 *   <li>Thread-safe implementation</li>
 * </ul>
 *
 * <p>The comparison process:
 * <ol>
 *   <li>Resolves both code types using the specified locale</li>
 *   <li>Handles null values by converting them to empty strings</li>
 *   <li>Performs a natural string comparison of the resolved values</li>
 * </ol>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create a comparator for German locale
 * CodeTypeComparator germanComparator = new CodeTypeComparator(Locale.GERMAN);
 *
 * // Sort a list of code types
 * List&lt;CodeType&gt; codes = new ArrayList&lt;&gt;();
 * codes.add(new CodeTypeImpl("Beta"));
 * codes.add(new CodeTypeImpl("Alpha"));
 * Collections.sort(codes, germanComparator);
 *
 * // Use in a TreeSet
 * Set&lt;CodeType&gt; sortedCodes = new TreeSet&lt;&gt;(germanComparator);
 * sortedCodes.addAll(codes);
 *
 * // For non-localized CodeTypes, null locale is acceptable
 * CodeTypeComparator simpleComparator = new CodeTypeComparator(null);
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>The comparator is immutable and thread-safe</li>
 *   <li>Null values in resolved strings are converted to empty strings</li>
 *   <li>The comparison is case-sensitive</li>
 *   <li>The locale parameter can be null for non-localized comparisons</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see CodeType
 * @see Comparator
 * @see Serializable
 */
@RequiredArgsConstructor
public class CodeTypeComparator implements Comparator<CodeType>, Serializable {

    @Serial
    private static final long serialVersionUID = 7747878519156301042L;

    /** The locale to use for resolving code type values. May be null. */
    private final Locale locale;

    /**
     * Compares two {@link CodeType} instances based on their resolved values.
     * The comparison is performed using the following steps:
     * <ol>
     *   <li>Resolve both code types using the configured locale</li>
     *   <li>Convert any null results to empty strings</li>
     *   <li>Perform a natural string comparison</li>
     * </ol>
     *
     * <p>This method is null-safe for both the code types and their resolved values.
     * Null resolved values are treated as empty strings for comparison purposes.
     *
     * @param type1 the first CodeType to compare
     * @param type2 the second CodeType to compare
     * @return a negative integer if type1 is less than type2,
     *         zero if they are equal,
     *         a positive integer if type1 is greater than type2
     */
    @Override
    public int compare(final CodeType type1, final CodeType type2) {
        final var type1Name = nullToEmpty(type1.getResolved(locale));
        final var type2Name = nullToEmpty(type2.getResolved(locale));

        return type1Name.compareTo(type2Name);
    }
}
