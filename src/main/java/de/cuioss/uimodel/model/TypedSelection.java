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
package de.cuioss.uimodel.model;

/**
 * Defines a type-safe contract for handling selections in UI models. This interface
 * provides a generic way to manage single-value selections while maintaining type
 * safety throughout the application.
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe selection handling</li>
 *   <li>Generic implementation for any value type</li>
 *   <li>Simple single-value selection model</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Dropdown/Select components</li>
 *   <li>Radio button groups</li>
 *   <li>Single-item pickers</li>
 *   <li>Type-safe option selection</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // String-based selection
 * public class StringSelection implements TypedSelection&lt;String&gt; {
 *     private String selected;
 *
 *     &#64;Override
 *     public String getSelectedValue() {
 *         return selected;
 *     }
 *
 *     public void setSelectedValue(String value) {
 *         this.selected = value;
 *     }
 * }
 *
 * // Enum-based selection
 * public class StatusSelection implements TypedSelection&lt;Status&gt; {
 *     private Status selectedStatus;
 *
 *     &#64;Override
 *     public Status getSelectedValue() {
 *         return selectedStatus;
 *     }
 *
 *     public void setSelectedStatus(Status status) {
 *         this.selectedStatus = status;
 *     }
 * }
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Implementations should handle null values appropriately</li>
 *   <li>Consider adding validation for the selected value if needed</li>
 *   <li>For collections of selectable items, consider using a separate collection property</li>
 * </ul>
 *
 * @param <T> The type of value that can be selected. This type parameter
 *            ensures type safety when working with selections.
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public interface TypedSelection<T> {

    /**
     * Retrieves the currently selected value.
     *
     * <p>The selected value represents the user's current choice from
     * the available options. This method may return null if no selection
     * has been made or if the selection has been cleared.
     *
     * @return the currently selected value, may be null if no selection exists
     */
    T getSelectedValue();

}
