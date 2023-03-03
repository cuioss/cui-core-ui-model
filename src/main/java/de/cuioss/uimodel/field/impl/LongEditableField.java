package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a number.
 *
 * @author Oliver Wolff
 */
public class LongEditableField extends BaseDynamicField<Long> {

    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * C'tor.
     *
     * @param editable
     *            If the input field is editable.
     */
    public LongEditableField(final boolean editable) {
        super(editable, DynamicFieldType.LONG);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input field
     * @param editable
     *            If the value is editable
     */
    public LongEditableField(final Long value, final boolean editable) {
        super(value, editable, DynamicFieldType.LONG);
    }

}
