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
package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Defines a field that supports dynamic value computation with change tracking capabilities.
 * This interface is particularly useful for implementing dynamic forms where values can be
 * modified and tracked for changes.
 *
 * <p>The interface provides:
 * <ul>
 *   <li>Value access and modification capabilities</li>
 *   <li>Change tracking to detect modifications</li>
 *   <li>Value reset functionality to revert changes</li>
 *   <li>Editability control to restrict modifications</li>
 * </ul>
 *
 * <p>Implementations must ensure:
 * <ul>
 *   <li>Thread-safety for concurrent access if required</li>
 *   <li>Proper change tracking when values are modified</li>
 *   <li>Consistent state management for editability</li>
 *   <li>Proper null handling for values</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create an editable string field
 * TracedDynamicField&lt;String&gt; field = new StringEditableField(true);
 * 
 * // Set and track value changes
 * field.setValue("Initial value");
 * assertTrue(field.isAvailable());  // Value is now available
 * assertTrue(field.isChanged());    // Value has been changed
 * 
 * // Reset tracked changes
 * field.resetChanged();
 * assertFalse(field.isChanged());   // Changes have been reset
 * 
 * // Control editability
 * field.setEditable(false);
 * assertFalse(field.isEditable());  // Field is now read-only
 * </pre>
 *
 * @author Eugen Fischer
 * @param <T> The type of value managed by this field. Must be {@link Serializable}
 *           to support persistence and distribution.
 * @since 1.0
 */
public interface TracedDynamicField<T extends Serializable> extends Serializable {

    /**
     * Determines if the field's value can be modified in its current state.
     * This is used to control whether UI components should allow editing and
     * whether setValue operations are permitted.
     *
     * @return {@code true} if the field can be modified, {@code false} otherwise
     */
    boolean isEditable();

    /**
     * Checks if a value is currently available for this field.
     * This is distinct from checking if the value is null, as it may
     * indicate whether the value has been initialized or loaded.
     *
     * @return {@code true} if a value is available, {@code false} otherwise
     */
    boolean isAvailable();

    /**
     * Retrieves the current value of the field.
     * 
     * <p>Note: A return value of null may indicate either that no value
     * has been set, or that null is a valid value for this field. Use
     * {@link #isAvailable()} to distinguish between these cases.
     *
     * @return The current value, or null if no value is available
     */
    T getValue();

    /**
     * Updates the field's value and marks the field as changed if the new
     * value differs from the current value.
     *
     * <p>Implementations should:
     * <ul>
     *   <li>Validate the new value if required</li>
     *   <li>Update the change tracking state</li>
     *   <li>Handle null values appropriately</li>
     * </ul>
     *
     * @param newValue The new value to set. May be null.
     * @throws IllegalStateException if the field is not editable
     */
    void setValue(T newValue);

    /**
     * Indicates whether the field's value has been modified since it was
     * last loaded or reset. This is useful for optimizing save operations
     * by only persisting changed values.
     *
     * @return {@code true} if the value has been modified, {@code false} otherwise
     */
    boolean isChanged();

    /**
     * Reverts the field's value to its original state and resets the
     * change tracking status. This is useful for implementing cancel
     * or reset functionality in forms.
     *
     * @return The original value that was restored
     */
    T resetValue();
}
