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
package de.cuioss.uimodel.model.code;

import de.cuioss.uimodel.model.Gender;

/**
 * Extends the {@link CodeType} interface to provide specialized handling for gender
 * information in the system. This interface combines the general code type capabilities
 * with gender-specific functionality, ensuring type-safe gender handling while
 * maintaining compatibility with the code system.
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe gender representation</li>
 *   <li>Integration with the {@link Gender} enumeration</li>
 *   <li>Code type compatibility for UI components</li>
 *   <li>Internationalization support</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Person/User profile management</li>
 *   <li>Form input validation</li>
 *   <li>Gender-specific UI adaptations</li>
 *   <li>Data export/import operations</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class GenderCode implements GenderCodeType {
 *     private final Gender gender;
 *
 *     public GenderCode(Gender gender) {
 *         this.gender = requireNonNull(gender);
 *     }
 *
 *     &#64;Override
 *     public Gender getGender() {
 *         return gender;
 *     }
 *
 *     &#64;Override
 *     public String getResolved(Locale locale) {
 *         // Use a ResourceBundle to get localized gender display
 *         return ResourceBundle.getBundle("messages", locale)
 *             .getString(gender.getLabelKey());
 *     }
 *
 *     &#64;Override
 *     public String getIdentifier() {
 *         return gender.name();
 *     }
 * }
 *
 * // Using the gender code type
 * GenderCodeType maleCode = new GenderCode(Gender.MALE);
 * String display = maleCode.getResolved(Locale.ENGLISH); // "Male"
 * Gender gender = maleCode.getGender(); // Gender.MALE
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Implementations should be immutable</li>
 *   <li>The gender value should never be null</li>
 *   <li>Consider caching resolved values for performance</li>
 *   <li>Ensure proper integration with the {@link Gender} enum</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see CodeType
 * @see Gender
 */
public interface GenderCodeType extends CodeType {

    /**
     * Retrieves the gender value associated with this code type.
     * This method provides type-safe access to the gender information,
     * ensuring that gender values are always valid according to the
     * {@link Gender} enumeration.
     *
     * <p>The gender value represents one of the predefined gender types
     * in the system and is used for:
     * <ul>
     *   <li>UI display and formatting</li>
     *   <li>Data validation</li>
     *   <li>Business logic processing</li>
     *   <li>Database persistence</li>
     * </ul>
     *
     * @return the associated gender value, never null
     * @see Gender
     */
    Gender getGender();

}
