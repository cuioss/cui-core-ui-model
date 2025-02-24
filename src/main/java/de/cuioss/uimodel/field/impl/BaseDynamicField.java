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

import de.cuioss.uimodel.field.DynamicField;
import de.cuioss.uimodel.field.DynamicFieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Base implementation for {@link DynamicField} that provides core functionality for
 * managing field values, tracking changes, and handling editability constraints.
 * This class serves as the foundation for type-specific field implementations.
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe value management</li>
 *   <li>Change tracking with old value preservation</li>
 *   <li>Editability control with runtime validation</li>
 *   <li>Field type metadata support</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Subclasses must specify their concrete type parameter</li>
 *   <li>Values are validated against editability constraints</li>
 *   <li>Change detection uses {@link Objects#equals(Object, Object)}</li>
 *   <li>Field type must be specified at construction and cannot be null</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create an editable integer field
 * var intField = new IntegerEditableField(true);
 * assertEquals(DynamicFieldType.INTEGER, intField.getFieldType());
 * assertFalse(intField.isAvailable());  // No value set yet
 * 
 * intField.setValue(5);
 * assertTrue(intField.isAvailable());   // Value is now set
 * assertTrue(intField.isChanged());     // Value has been changed
 * 
 * // Create a read-only string field with initial value
 * var stringField = new StringEditableField("initial", false);
 * assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
 * assertTrue(stringField.isAvailable());
 * assertFalse(stringField.isEditable());
 * 
 * // Attempting to modify a read-only field throws exception
 * assertThrows(IllegalStateException.class, () -> stringField.setValue("new"));
 * </pre>
 *
 * @author Oliver Wolff
 * @param <T> The concrete type for this field, must implement {@link Serializable}
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
public abstract class BaseDynamicField<T extends Serializable> implements DynamicField<T> {

    @Serial
    private static final long serialVersionUID = 7865845990018198224L;

    /** Indicates whether the field can be modified. */
    @Getter
    private final boolean editable;

    /** The current value of the field. */
    @Getter
    private T value;

    /** Previous value, used for change tracking and reset functionality. */
    private T oldValue;

    /** Tracks whether the value has been modified from its original state. */
    @Getter
    private boolean changed = false;

    /** The type metadata for this field, providing type-specific behavior. */
    @Getter
    private final DynamicFieldType fieldType;

    /**
     * Constructs a new field with the specified editability and field type.
     * The initial value will be null.
     *
     * @param editable  defines whether the field is editable
     * @param fieldType the content type of this field, must not be null
     * @throws NullPointerException if fieldType is null
     */
    protected BaseDynamicField(final boolean editable, final DynamicFieldType fieldType) {
        this(null, editable, fieldType);
    }

    /**
     * Constructs a new field with the specified value, editability, and field type.
     *
     * @param value     the initial value, may be null
     * @param editable  defines whether the field is editable
     * @param fieldType the content type of this field, must not be null
     * @throws NullPointerException if fieldType is null
     */
    protected BaseDynamicField(final T value, final boolean editable, final DynamicFieldType fieldType) {
        this.value = value;
        oldValue = value;
        this.editable = editable;
        this.fieldType = requireNonNull(fieldType);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException if the field is not editable and a value
     *         modification is attempted
     */
    @Override
    public void setValue(final T newValue) {
        oldValue = value;
        value = checkValueIsMutable(newValue);
        changed = !Objects.equals(oldValue, value);
    }

    /**
     * Validates that the field is editable before allowing value modification.
     *
     * @param newValue the new value to set
     * @return the new value if the field is editable
     * @throws IllegalStateException if the field is not editable
     */
    private T checkValueIsMutable(final T newValue) {
        if (editable) {
            return newValue;
        }
        throw new IllegalStateException("Not allowed to edit value");
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAvailable() {
        return null != value;
    }

    /** {@inheritDoc} */
    @Override
    public T resetValue() {
        value = oldValue;
        changed = false;
        return getValue();
    }
}
