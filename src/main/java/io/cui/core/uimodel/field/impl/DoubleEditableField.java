package io.cui.core.uimodel.field.impl;

import io.cui.core.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a {@link Double}.
 *
 * @author Oliver Wolff
 */
public class DoubleEditableField extends BaseDynamicField<Double> {

    /** */
    private static final long serialVersionUID = 4466539654104251395L;

    /**
     * C'tor.
     *
     * @param editable
     *            If the input field is editable.
     */
    public DoubleEditableField(final boolean editable) {
        super(editable, DynamicFieldType.DOUBLE);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input field
     * @param editable
     *            If the value is editable
     */
    public DoubleEditableField(final Double value, final boolean editable) {
        super(value, editable, DynamicFieldType.DOUBLE);
    }

}
