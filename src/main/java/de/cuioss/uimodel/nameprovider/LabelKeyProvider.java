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

/**
 * A functional interface that defines objects capable of providing label keys
 * for resource bundle lookups. This interface is typically used in conjunction
 * with {@link LabeledKey} to enable internationalization (i18n) support.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Functional interface design</li>
 *   <li>Resource bundle integration</li>
 *   <li>Simple contract</li>
 *   <li>Lambda-friendly</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Simple implementation
 * public class UserStatus implements LabelKeyProvider {
 *     public String getLabelKey() {
 *         return "user.status.label";
 *     }
 * }
 *
 * // Enum implementation
 * public enum DocumentType implements LabelKeyProvider {
 *     PDF("document.type.pdf"),
 *     WORD("document.type.word");
 *
 *     private final String labelKey;
 *
 *     DocumentType(String key) {
 *         this.labelKey = key;
 *     }
 *
 *     public String getLabelKey() {
 *         return labelKey;
 *     }
 * }
 *
 * // Lambda usage
 * LabelKeyProvider provider = () -> "button.save";
 * </pre>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Enum type labels</li>
 *   <li>Status indicators</li>
 *   <li>Button labels</li>
 *   <li>Field labels</li>
 *   <li>Menu items</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Implementations should return consistent keys</li>
 *   <li>Keys should follow the application's naming convention</li>
 *   <li>Consider using constants for frequently used keys</li>
 *   <li>Keys should exist in the resource bundles</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see LabeledKey
 * @see java.util.ResourceBundle
 * @since 1.0
 */
@FunctionalInterface
public interface LabelKeyProvider {

    /**
     * Returns a label key that can be used for resource bundle lookups.
     * This key should correspond to a valid entry in the application's
     * resource bundles.
     *
     * <p>Implementation Guidelines:
     * <ul>
     *   <li>Return a non-null key</li>
     *   <li>Use consistent naming patterns</li>
     *   <li>Ensure key exists in resource bundles</li>
     *   <li>Consider caching if key generation is expensive</li>
     * </ul>
     *
     * @return a label key corresponding to the given object
     */
    String getLabelKey();
}
