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
import de.cuioss.uimodel.field.UnlockableTracedDynamicField;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @param <T>
 *
 * @author Eugen Fischer
 */
@ToString(doNotUseGetters = true, of = {"currentValue", "currentEditableValue"})
@EqualsAndHashCode(doNotUseGetters = true, of = {"currentValue", "currentEditableValue"})
public class UnlockableTracedDynamicFieldImpl<T extends Serializable> implements UnlockableTracedDynamicField<T> {

    @Serial
    private static final long serialVersionUID = 4631584209321440539L;

    private final T defaultValue;

    private final boolean defaultEditableValue;

    private boolean currentEditableValue;

    private T currentValue;

    /**
     * Initialization of {@linkplain TracedDynamicField}
     *
     * @param initialValue  which should be stored
     * @param fieldEditable define if value could be changed
     */
    public UnlockableTracedDynamicFieldImpl(final T initialValue, final boolean fieldEditable) {
        defaultValue = initialValue;
        defaultEditableValue = fieldEditable;
        resetValue();
    }

    @Override
    public boolean isEditable() {
        return currentEditableValue;
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
        if (null == defaultValue) {
            return null != currentValue;
        }
        return !defaultValue.equals(currentValue);
    }

    @Override
    public T resetValue() {
        currentValue = defaultValue;
        currentEditableValue = defaultEditableValue;
        return currentValue;
    }

    @Override
    public void unlockEditMode() {
        if (!currentEditableValue) {
            currentEditableValue = !currentEditableValue;
        }
    }

    @Override
    public boolean isEditModeEnforced() {
        return currentEditableValue != defaultEditableValue;
    }

    @Override
    public void resetEditMode() {
        resetValue();
    }
}
