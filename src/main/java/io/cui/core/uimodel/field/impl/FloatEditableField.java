package io.cui.core.uimodel.field.impl;

import io.cui.core.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a {@link Float}.
 *
 * @author Oliver Wolff
 */
public class FloatEditableField extends BaseDynamicField<Float> {

    /** */
    private static final long serialVersionUID = 4466539654104251395L;

    /**
     * C'tor.
     *
     * @param editable
     *            If the input field is editable.
     */
    public FloatEditableField(final boolean editable) {
        super(editable, DynamicFieldType.FLOAT);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input field
     * @param editable
     *            If the value is editable
     */
    public FloatEditableField(final Float value, final boolean editable) {
        super(value, editable, DynamicFieldType.FLOAT);
    }

}
