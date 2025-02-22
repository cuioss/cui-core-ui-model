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
package de.cuioss.uimodel.model.conceptkey;

import java.io.Serializable;

/**
 * Defines a category system for {@link ConceptKeyType} instances, providing
 * classification and factory capabilities for concept keys. Categories help
 * organize and validate concept keys while ensuring proper augmentation
 * handling.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Unique category identification</li>
 *   <li>Factory methods for undefined concepts</li>
 *   <li>Serialization support</li>
 *   <li>Immutable design</li>
 * </ul>
 *
 * <p>Common Categories:
 * <ul>
 *   <li>Diagnosis codes</li>
 *   <li>Procedure codes</li>
 *   <li>Medication codes</li>
 *   <li>Laboratory values</li>
 *   <li>Clinical findings</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public enum MedicalCategory implements ConceptCategory {
 *     DIAGNOSIS("diagnosis"),
 *     PROCEDURE("procedure"),
 *     MEDICATION("medication");
 *
 *     private final String name;
 *
 *     MedicalCategory(String name) {
 *         this.name = name;
 *     }
 *
 *     &#64;Override
 *     public String getName() {
 *         return name;
 *     }
 *
 *     &#64;Override
 *     public ConceptKeyType createUndefinedConceptKey(String value) {
 *         return new UndefinedConceptKey(value, this);
 *     }
 * }
 *
 * // Using the category
 * ConceptCategory category = MedicalCategory.DIAGNOSIS;
 * ConceptKeyType undefined = category.createUndefinedConceptKey("ICD10-X99");
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Categories should be implemented as enums where possible</li>
 *   <li>Category names should be unique within the system</li>
 *   <li>Consider internationalization needs</li>
 *   <li>Undefined concept keys should maintain traceability</li>
 * </ul>
 *
 * @author Matthias Walliczek
 * @since 1.0
 * @see ConceptKeyType
 * @see AugmentationKeyConstants
 * @see Serializable
 */
public interface ConceptCategory extends Serializable {

    /**
     * Returns the unique name (identifier) for this category. This name is used
     * internally to identify and distinguish between different categories.
     *
     * <p>Requirements:
     * <ul>
     *   <li>Must be unique within the system</li>
     *   <li>Should be consistent across system restarts</li>
     *   <li>Should follow a consistent naming convention</li>
     *   <li>Should be lowercase, using underscores for spaces</li>
     * </ul>
     *
     * @return the internal name of this category, never null
     */
    String getName();

    /**
     * Creates a new undefined concept key for the given value within this category.
     * Undefined concept keys are used to handle cases where a concept identifier
     * is known but its full definition is not available or not yet loaded.
     *
     * <p>The created concept key will:
     * <ul>
     *   <li>Belong to this category</li>
     *   <li>Have the specified value as identifier</li>
     *   <li>Be marked with {@link AugmentationKeyConstants#UNDEFINED_VALUE}</li>
     *   <li>Maintain traceability of its undefined status</li>
     * </ul>
     *
     * @param value the identifier for the undefined concept
     * @return a new ConceptKeyType instance marked as undefined
     * @throws IllegalArgumentException if value is null or empty
     */
    ConceptKeyType createUndefinedConceptKey(String value);
}
