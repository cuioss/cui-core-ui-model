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

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import de.cuioss.uimodel.field.DynamicField;
import de.cuioss.uimodel.field.DynamicFieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base implementation for {@link DynamicField}
 *
 * @author Oliver Wolff
 * @param <T> defining the concrete type for this field
 */
@EqualsAndHashCode
@ToString
public abstract class BaseDynamicField<T extends Serializable> implements DynamicField<T> {

    private static final long serialVersionUID = 7865845990018198224L;

    @Getter
    private final boolean editable;

    @Getter
    private T value;

    private T oldValue;

    @Getter
    private boolean changed = false;

    @Getter
    private final DynamicFieldType fieldType;

    /**
     * Constructor.
     *
     * @param editable  defines whether the field in question is editable or not
     * @param fieldType the content type of this field. It must not be null
     */
    protected BaseDynamicField(final boolean editable, final DynamicFieldType fieldType) {
        this(null, editable, fieldType);
    }

    /**
     * Constructor.
     *
     * @param value     defining the initial value, may be null
     * @param editable  defines whether the field in question is editable or not
     * @param fieldType the content type of this field. It must not be null
     */
    protected BaseDynamicField(final T value, final boolean editable, final DynamicFieldType fieldType) {
        this.value = value;
        oldValue = value;
        this.editable = editable;
        this.fieldType = requireNonNull(fieldType);
    }

    @Override
    public void setValue(final T newValue) {
        oldValue = value;
        value = checkValueIsMutable(newValue);
        changed = !Objects.equals(oldValue, value);
    }

    private T checkValueIsMutable(final T newValue) {
        if (editable) {
            return newValue;
        }
        throw new IllegalStateException("Not allowed to edit value");
    }

    @Override
    public boolean isAvailable() {
        return null != value;
    }

    @Override
    public T resetValue() {
        value = oldValue;
        changed = false;
        return getValue();
    }

}
