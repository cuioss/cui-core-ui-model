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
package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.TracedDynamicField;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Default implementation of {@link TracedDynamicField} that provides fundamental
 * value tracking and state management capabilities. This class serves as a base
 * implementation for fields that need to track changes and maintain state.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Maintains original (default) and current values</li>
 *   <li>Tracks modifications through value comparison</li>
 *   <li>Supports value reset functionality</li>
 *   <li>Controls editability state</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Value comparison uses {@link Objects#equals(Object, Object)}</li>
 *   <li>Null values are handled safely</li>
 *   <li>The default value is immutable after construction</li>
 *   <li>Editability state is fixed at construction</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create a traced field with initial value
 * BaseTracedDynamicField<String> field = new BaseTracedDynamicField<>("initial", true);
 * assertTrue(field.isAvailable());
 * assertFalse(field.isChanged());
 * 
 * // Modify value and track changes
 * field.setValue("modified");
 * assertTrue(field.isChanged());
 * assertEquals("modified", field.getValue());
 * 
 * // Reset value to initial state
 * field.resetValue();
 * assertFalse(field.isChanged());
 * assertEquals("initial", field.getValue());
 * 
 * // Create a read-only field
 * field = new BaseTracedDynamicField<>(null, false);
 * assertFalse(field.isEditable());
 * assertThrows(IllegalStateException.class, () -> field.setValue("new"));
 * </pre>
 *
 * @author Eugen Fischer
 * @param <T> The type of value managed by this field, must implement {@link Serializable}
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
public class BaseTracedDynamicField<T extends Serializable> implements TracedDynamicField<T> {

    @Serial
    private static final long serialVersionUID = -5393234651696267199L;

    /** The original value that serves as the reset target. */
    private final T defaultValue;

    /** Controls whether the field's value can be modified. */
    private final boolean editable;

    /** The current value of the field, may differ from defaultValue. */
    private T currentValue;

    /**
     * Constructs a new traced field with the specified initial value and
     * editability state. The initial value becomes the default value for
     * reset operations.
     *
     * @param initialValue  The value to store as both current and default value
     * @param fieldEditable Whether the field's value can be modified
     */
    public BaseTracedDynamicField(final T initialValue, final boolean fieldEditable) {
        defaultValue = initialValue;
        editable = fieldEditable;
        resetValue();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEditable() {
        return editable;
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
        return !Objects.equals(defaultValue, currentValue);
    }

    /** {@inheritDoc} */
    @Override
    public T resetValue() {
        currentValue = defaultValue;
        return currentValue;
    }
}
