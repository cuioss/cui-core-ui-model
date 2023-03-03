package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.DynamicFieldType;

/**
 * An input field where the user can type in a String.
 *
 * @author Oliver Wolff
 */
public class StringEditableField extends BaseDynamicField<String> {

    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * C'tor.
     *
     * @param editable
     *            If the content of the field is editable.
     */
    public StringEditableField(final boolean editable) {
        super(editable, DynamicFieldType.STRING);
    }

    /**
     * C'tor.
     *
     * @param value
     *            The value of the input field
     * @param editable
     *            If the input field is editable.
     */
    public StringEditableField(final String value, final boolean editable) {
        super(value, editable, DynamicFieldType.STRING);
    }
}
