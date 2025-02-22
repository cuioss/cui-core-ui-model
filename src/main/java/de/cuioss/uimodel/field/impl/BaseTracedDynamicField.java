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
 * Default implementation of the {@linkplain TracedDynamicField} interface.
 *
 * @author Eugen Fischer
 * @param <T>
 */
@ToString
@EqualsAndHashCode
public class BaseTracedDynamicField<T extends Serializable> implements TracedDynamicField<T> {

    @Serial
    private static final long serialVersionUID = -5393234651696267199L;

    private final T defaultValue;

    private final boolean editable;

    private T currentValue;

    /**
     * Initialization of {@linkplain TracedDynamicField}
     *
     * @param initialValue  which should be stored
     * @param fieldEditable define if value could be changed
     */
    public BaseTracedDynamicField(final T initialValue, final boolean fieldEditable) {
        defaultValue = initialValue;
        editable = fieldEditable;
        resetValue();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public boolean isAvailable() {
        return null != currentValue;
    }

    @Override
    public T getValue() {
        return currentValue;
    }

    @Override
    public void setValue(final T newValue) {
        currentValue = newValue;
    }

    @Override
    public boolean isChanged() {
        return !Objects.equals(defaultValue, currentValue);
    }

    @Override
    public T resetValue() {
        currentValue = defaultValue;
        return currentValue;
    }
}
