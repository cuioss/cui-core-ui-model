package de.cuioss.uimodel.field.impl;

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
