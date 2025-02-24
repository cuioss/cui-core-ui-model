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

import de.cuioss.uimodel.model.TypedSelection;

/**
 * Specializes {@link TypedSelection} for handling selections of {@link ConceptKeyType}
 * instances in UI models. This interface provides type-safe selection capabilities
 * specifically tailored for concept-based data structures.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Type-safe concept key selection</li>
 *   <li>UI model integration</li>
 *   <li>Selection state management</li>
 *   <li>Category-aware selection</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Diagnosis code selection</li>
 *   <li>Procedure picker components</li>
 *   <li>Medical code lookup</li>
 *   <li>Category-filtered selection</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class DiagnosisSelection implements ConceptKeyTypeSelection {
 *     private ConceptKeyType selectedDiagnosis;
 *
 *     &#64;Override
 *     public ConceptKeyType getSelectedValue() {
 *         return selectedDiagnosis;
 *     }
 *
 *     public void setSelectedValue(ConceptKeyType diagnosis) {
 *         if (diagnosis != null
 *             &amp;&amp; diagnosis.getCategory() == MedicalCategory.DIAGNOSIS) {
 *             this.selectedDiagnosis = diagnosis;
 *         }
 *     }
 *
 *     public boolean hasSelection() {
 *         return selectedDiagnosis != null;
 *     }
 * }
 *
 * // Using the selection
 * DiagnosisSelection selection = new DiagnosisSelection();
 * selection.setSelectedValue(diagnosisCode);
 * if (selection.hasSelection()) {
 *     ConceptKeyType selected = selection.getSelectedValue();
 *     // Process selected diagnosis
 * }
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Consider category validation in setters</li>
 *   <li>Handle null values appropriately</li>
 *   <li>Implement proper state management</li>
 *   <li>Consider adding selection change listeners</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see TypedSelection
 * @see ConceptKeyType
 */
public interface ConceptKeyTypeSelection extends TypedSelection<ConceptKeyType> {

    /**
     * Returns the currently selected concept key.
     *
     * <p>This method overrides {@link TypedSelection#getSelectedValue()} to provide:
     * <ul>
     *   <li>More specific return type</li>
     *   <li>Clearer contract for concept key handling</li>
     *   <li>Better IDE support through explicit typing</li>
     * </ul>
     *
     * @return the currently selected concept key, may be null if no selection exists
     */
    @Override
    ConceptKeyType getSelectedValue();

}
