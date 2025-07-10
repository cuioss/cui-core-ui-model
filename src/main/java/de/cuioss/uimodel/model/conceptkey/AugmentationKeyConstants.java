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
package de.cuioss.uimodel.model.conceptkey;

import lombok.experimental.UtilityClass;

/**
 * Provides constants and utility methods for handling concept key augmentation
 * in the UI model. This class defines standard augmentation keys and methods
 * to check their states, enabling consistent handling of special concept states
 * across the application.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Standard augmentation key definitions</li>
 *   <li>Null-safe state checking</li>
 *   <li>Boolean value parsing</li>
 *   <li>Utility method access</li>
 * </ul>
 *
 * <p>Available Augmentations:
 * <ul>
 *   <li>{@link #DEFAULT_VALUE} - Marks default selections</li>
 *   <li>{@link #UNDEFINED_VALUE} - Marks undefined or invalid states</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class DiagnosisCode implements ConceptKeyType {
 *     private final Map&lt;String, String&gt; augmentations = new HashMap&lt;&gt;();
 *
 *     public void markAsDefault() {
 *         augmentations.put(AugmentationKeyConstants.DEFAULT_VALUE, "true");
 *     }
 *
 *     public void markAsUndefined() {
 *         augmentations.put(AugmentationKeyConstants.UNDEFINED_VALUE, "true");
 *     }
 *
 *     public boolean isSelectable() {
 *         return !AugmentationKeyConstants.isUndefinedValue(this);
 *     }
 *
 *     public boolean isDefaultOption() {
 *         return AugmentationKeyConstants.isDefaultValue(this);
 *     }
 * }
 *
 * // Using the augmentations
 * DiagnosisCode code = new DiagnosisCode();
 * code.markAsDefault();
 *
 * if (code.isDefaultOption()) {
 *     // Handle default selection
 * }
 *
 * if (!code.isSelectable()) {
 *     // Handle disabled state
 * }
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>All methods are null-safe</li>
 *   <li>Boolean values are parsed case-insensitively</li>
 *   <li>Constants are immutable</li>
 *   <li>The class cannot be instantiated</li>
 * </ul>
 *
 * @author Matthias Walliczek
 * @since 1.0
 * @see ConceptKeyType
 */
@UtilityClass
public final class AugmentationKeyConstants {

    /**
     * Key constant used to mark a concept as the default value within its category.
     * When this augmentation is present and set to "true", the concept should be
     * considered as the default selection in UI components.
     *
     * <p>Common uses include:
     * <ul>
     *   <li>Pre-selecting items in dropdowns</li>
     *   <li>Setting default form values</li>
     *   <li>Defining fallback options</li>
     * </ul>
     */
    public static final String DEFAULT_VALUE = "default_value";

    /**
     * Key constant used to mark a concept as undefined or invalid. When this
     * augmentation is present and set to "true", the concept should be considered
     * as not fully defined or temporarily invalid.
     *
     * <p>Common uses include:
     * <ul>
     *   <li>Disabling selection in UI components</li>
     *   <li>Marking placeholder entries</li>
     *   <li>Indicating incomplete data</li>
     *   <li>Handling migration states</li>
     * </ul>
     */
    public static final String UNDEFINED_VALUE = "undefined_value";

    /**
     * Checks if the given concept key is marked as undefined.
     *
     * <p>A concept is considered undefined when:
     * <ul>
     *   <li>The concept has the {@link #UNDEFINED_VALUE} augmentation key</li>
     *   <li>The value for this key is "true" (case-insensitive)</li>
     * </ul>
     *
     * @param codeType the concept key to check, may be null
     * @return true if the concept is marked as undefined, false otherwise
     */
    public static boolean isUndefinedValue(final ConceptKeyType codeType) {
        return null != codeType && null != codeType.get(AugmentationKeyConstants.UNDEFINED_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstants.UNDEFINED_VALUE));
    }

    /**
     * Checks if the given concept key is marked as the default value.
     *
     * <p>A concept is considered the default when:
     * <ul>
     *   <li>The concept has the {@link #DEFAULT_VALUE} augmentation key</li>
     *   <li>The value for this key is "true" (case-insensitive)</li>
     * </ul>
     *
     * @param codeType the concept key to check, may be null
     * @return true if the concept is marked as default, false otherwise
     */
    public static boolean isDefaultValue(final ConceptKeyType codeType) {
        return null != codeType && null != codeType.get(AugmentationKeyConstants.DEFAULT_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstants.DEFAULT_VALUE));
    }
}
