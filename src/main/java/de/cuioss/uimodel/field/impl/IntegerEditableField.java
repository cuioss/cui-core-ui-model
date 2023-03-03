package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a number.
 *
 * @author Oliver Wolff
 */
public class IntegerEditableField extends BaseDynamicField<Integer> {

    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * C'tor.
     *
     * @param editable
     *            If the input field is editable.
     */
    public IntegerEditableField(final boolean editable) {
        super(editable, DynamicFieldType.INTEGER);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input field
     * @param editable
     *            If the value is editable
     */
    public IntegerEditableField(final Integer value, final boolean editable) {
        super(value, editable, DynamicFieldType.INTEGER);
    }

}
