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
package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.UnlockableTracedDynamicField;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Default implementation of {@link UnlockableTracedDynamicField} that provides
 * protected field functionality with temporary unlocking capabilities. This implementation
 * maintains both the field's value and its editability state.
 *
 * <p>Features:
 * <ul>
 *   <li>Independent tracking of value and editability state</li>
 *   <li>Support for temporary unlocking of read-only fields</li>
 *   <li>Reset capability for both value and editability</li>
 *   <li>Proper null value handling</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>The default editability state is preserved for reset operations</li>
 *   <li>Change detection properly handles null values</li>
 *   <li>Unlocking is only effective when the field is not already editable</li>
 *   <li>State changes are tracked independently of value changes</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create a protected field with initial value
 * UnlockableTracedDynamicField&lt;String&gt; field = new UnlockableTracedDynamicFieldImpl&lt;&gt;(
 *     "Protected Value",  // Initial value
 *     false              // Initially read-only
 * );
 *
 * // Field starts as read-only
 * assert !field.isEditable();
 *
 * // Temporarily unlock for editing
 * field.unlockEditMode();
 * assert field.isEditable();
 * assert field.isEditModeEnforced();
 *
 * // Reset to original state
 * field.resetEditMode();
 * assert !field.isEditable();
 * </pre>
 *
 * @param <T> The type of value managed by this field, must implement {@link Serializable}
 * @author Eugen Fischer
 * @since 1.0
 */
@ToString(doNotUseGetters = true, of = {"currentValue", "currentEditableValue"})
@EqualsAndHashCode(doNotUseGetters = true, of = {"currentValue", "currentEditableValue"})
public class UnlockableTracedDynamicFieldImpl<T extends Serializable> implements UnlockableTracedDynamicField<T> {

    @Serial
    private static final long serialVersionUID = 4631584209321440539L;

    /** The original value that serves as the reset target. */
    private final T defaultValue;

    /** The original editability state that serves as the reset target. */
    private final boolean defaultEditableValue;

    /** The current editability state, may be temporarily enforced. */
    private boolean currentEditableValue;

    /** The current value of the field, may differ from defaultValue. */
    private T currentValue;

    /**
     * Constructs a new unlockable field with the specified initial value and
     * editability state. Both the value and editability state become the
     * default state for reset operations.
     *
     * @param initialValue  The value to store as both current and default value
     * @param fieldEditable The initial and default editability state
     */
    public UnlockableTracedDynamicFieldImpl(final T initialValue, final boolean fieldEditable) {
        defaultValue = initialValue;
        defaultEditableValue = fieldEditable;
        resetValue();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEditable() {
        return currentEditableValue;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAvailable() {
        return null != currentValue;
    }

    /** {@inheritDoc} */
    @Override
    public T getValue() {
        return currentValue;
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(final T newValue) {
        currentValue = newValue;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isChanged() {
        if (null == defaultValue) {
            return null != currentValue;
        }
        return !defaultValue.equals(currentValue);
    }

    /** {@inheritDoc} */
    @Override
    public T resetValue() {
        currentValue = defaultValue;
        currentEditableValue = defaultEditableValue;
        return currentValue;
    }

    /** {@inheritDoc} */
    @Override
    public void unlockEditMode() {
        if (!currentEditableValue) {
            currentEditableValue = !currentEditableValue;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEditModeEnforced() {
        return currentEditableValue != defaultEditableValue;
    }

    /** {@inheritDoc} */
    @Override
    public void resetEditMode() {
        resetValue();
    }
}
