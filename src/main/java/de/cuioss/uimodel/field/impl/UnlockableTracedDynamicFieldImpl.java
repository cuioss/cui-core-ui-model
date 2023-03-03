package de.cuioss.uimodel.field.impl;

import java.io.Serializable;

import de.cuioss.uimodel.field.TracedDynamicField;
import de.cuioss.uimodel.field.UnlockableTracedDynamicField;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @param <T>
 *
 * @author Eugen Fischer
 */
@ToString(doNotUseGetters = true, of = { "currentValue", "currentEditableValue" })
@EqualsAndHashCode(doNotUseGetters = true, of = { "currentValue", "currentEditableValue" })
public class UnlockableTracedDynamicFieldImpl<T extends Serializable> implements UnlockableTracedDynamicField<T> {

    private static final long serialVersionUID = 4631584209321440539L;

    private final T defaultValue;

    private final boolean defaultEditableValue;

    private boolean currentEditableValue;

    private T currentValue;

    /**
     * Initialization of {@linkplain TracedDynamicField}
     *
     * @param initialValue which should be stored
     * @param fieldEditable define if value could be changed
     */
    public UnlockableTracedDynamicFieldImpl(final T initialValue, final boolean fieldEditable) {
        this.defaultValue = initialValue;
        this.defaultEditableValue = fieldEditable;
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
        this.currentValue = defaultValue;
        this.currentEditableValue = defaultEditableValue;
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
