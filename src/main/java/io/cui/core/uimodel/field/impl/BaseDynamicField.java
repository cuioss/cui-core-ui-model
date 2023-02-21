package io.cui.core.uimodel.field.impl;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import io.cui.core.uimodel.field.DynamicField;
import io.cui.core.uimodel.field.DynamicFieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base implementation for {@link DynamicField}
 *
 * @author Oliver Wolff
 * @param <T>
 *            defining the concrete type for this field
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
     * @param editable
     *            defines whether the field in question is editable or not
     * @param fieldType
     *            the content type of this field. It must not be null
     */
    protected BaseDynamicField(final boolean editable, final DynamicFieldType fieldType) {
        this(null, editable, fieldType);
    }

    /**
     * Constructor.
     *
     * @param value
     *            defining the initial value, may be null
     * @param editable
     *            defines whether the field in question is editable or not
     * @param fieldType
     *            the content type of this field. It must not be null
     */
    protected BaseDynamicField(final T value, final boolean editable,
            final DynamicFieldType fieldType) {
        this.value = value;
        this.oldValue = value;
        this.editable = editable;
        this.fieldType = requireNonNull(fieldType);
    }

    @Override
    public void setValue(final T newValue) {
        this.oldValue = this.value;
        this.value = checkValueIsMutable(newValue);
        this.changed = !Objects.equals(oldValue, value);
    }

    private T checkValueIsMutable(final T newValue) {
        if (this.editable) {
            return newValue;
        }
        throw new IllegalStateException("Not allowed to edit value");
    }

    @Override
    public boolean isAvailable() {
        return null != this.value;
    }

    @Override
    public T resetValue() {
        this.value = this.oldValue;
        this.changed = false;
        return getValue();
    }

}
