package de.cuioss.uimodel.field.impl;

import java.io.Serializable;
import java.util.Objects;

import de.cuioss.uimodel.field.TracedDynamicField;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default implementation of the {@linkplain TracedDynamicField} interface.
 *
 * @author Eugen Fischer
 * @param <T>
 */
@ToString
@EqualsAndHashCode
public class BaseTracedDynamicField<T extends Serializable> implements TracedDynamicField<T> {

    private static final long serialVersionUID = -5393234651696267199L;

    private final T defaultValue;

    private final boolean editable;

    private T currentValue;

    /**
     * Initialization of {@linkplain TracedDynamicField}
     *
     * @param initialValue which should be stored
     * @param fieldEditable define if value could be changed
     */
    public BaseTracedDynamicField(final T initialValue, final boolean fieldEditable) {
        this.defaultValue = initialValue;
        this.editable = fieldEditable;
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
        this.currentValue = defaultValue;
        return currentValue;
    }
}
