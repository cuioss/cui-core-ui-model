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

import java.io.Serial;
import java.io.Serializable;

import de.cuioss.uimodel.field.DynamicField;
import de.cuioss.uimodel.field.DynamicFieldType;
import de.cuioss.uimodel.field.LabeledDynamicField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author Matthias Walliczek
 * @param <T>
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class BaseLabeledDynamicField<T extends Serializable> implements LabeledDynamicField<T> {

    @Serial
    private static final long serialVersionUID = -3949672012801403859L;

    @NonNull
    private final DynamicField<T> delegate;

    @Getter
    @NonNull
    private final String identifier;

    @Getter
    private final String labelKey;

    @Getter
    private final String advisoryKey;

    @Override
    public DynamicFieldType getFieldType() {
        return delegate.getFieldType();
    }

    @Override
    public boolean isEditable() {
        return delegate.isEditable();
    }

    @Override
    public boolean isAvailable() {
        return delegate.isAvailable();
    }

    @Override
    public T getValue() {
        return delegate.getValue();
    }

    @Override
    public void setValue(final T newValue) {
        delegate.setValue(newValue);
    }

    @Override
    public boolean isChanged() {
        return delegate.isChanged();
    }

    @Override
    public T resetValue() {
        return delegate.resetValue();
    }
}
