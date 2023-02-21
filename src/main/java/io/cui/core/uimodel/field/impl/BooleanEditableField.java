package io.cui.core.uimodel.field.impl;

import io.cui.core.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can select yes / no.
 *
 * @author Oliver Wolff
 */
public class BooleanEditableField extends BaseDynamicField<Boolean> {

    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * C'tor.
     *
     * @param editable
     *            If editable
     */
    public BooleanEditableField(final boolean editable) {
        super(editable, DynamicFieldType.BOOLEAN);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input.
     * @param editable
     *            If editable.
     */
    public BooleanEditableField(final Boolean value, final boolean editable) {
        super(value, editable, DynamicFieldType.BOOLEAN);
    }

}
