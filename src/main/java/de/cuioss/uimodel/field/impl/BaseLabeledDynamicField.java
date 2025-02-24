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
import de.cuioss.uimodel.field.LabeledDynamicField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base implementation of {@link LabeledDynamicField} that combines dynamic field
 * functionality with labeling capabilities through delegation. This class uses the
 * decorator pattern to add labeling support to any {@link DynamicField} implementation.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Delegates all field operations to an underlying {@link DynamicField}</li>
 *   <li>Adds label and advisory text support through resource keys</li>
 *   <li>Provides unique field identification</li>
 *   <li>Maintains immutable labeling properties</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>The delegate field must not be null</li>
 *   <li>The identifier must not be null</li>
 *   <li>Label and advisory keys may be null if not needed</li>
 *   <li>All field operations are forwarded to the delegate</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create a dynamic field using factory
 * DynamicField&lt;String&gt; textField = DynamicFieldType.STRING.createDynamicField("John Doe", true);
 *
 * // Add labeling support
 * LabeledDynamicField&lt;String&gt; labeledField = new BaseLabeledDynamicField&lt;&gt;(
 *     textField,                  // The field to decorate
 *     "userNameField",           // Unique identifier
 *     "user.name.label",        // Resource key for label
 *     "user.name.help"          // Resource key for help text
 * );
 * </pre>
 *
 * @author Matthias Walliczek
 * @param <T> The type of value managed by this field, must implement {@link Serializable}
 * @since 1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class BaseLabeledDynamicField<T extends Serializable> implements LabeledDynamicField<T> {

    @Serial
    private static final long serialVersionUID = -3949672012801403859L;

    /** The underlying field that handles all dynamic field operations. */
    @NonNull
    private final DynamicField<T> delegate;

    /** Unique identifier for this field, used in UI rendering. */
    @Getter
    @NonNull
    private final String identifier;

    /** Resource key for looking up the field's label text. */
    @Getter
    private final String labelKey;

    /** Resource key for looking up the field's advisory/help text. */
    @Getter
    private final String advisoryKey;

    /** {@inheritDoc} */
    @Override
    public DynamicFieldType getFieldType() {
        return delegate.getFieldType();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEditable() {
        return delegate.isEditable();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isAvailable() {
        return delegate.isAvailable();
    }

    /** {@inheritDoc} */
    @Override
    public T getValue() {
        return delegate.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(final T newValue) {
        delegate.setValue(newValue);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isChanged() {
        return delegate.isChanged();
    }

    /** {@inheritDoc} */
    @Override
    public T resetValue() {
        return delegate.resetValue();
    }
}
