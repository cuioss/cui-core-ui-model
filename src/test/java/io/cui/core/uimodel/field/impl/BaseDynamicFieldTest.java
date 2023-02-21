package io.cui.core.uimodel.field.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.cui.core.uimodel.field.DynamicFieldType;
import io.cui.test.valueobjects.ValueObjectTest;
import io.cui.test.valueobjects.api.contracts.VerifyConstructor;

@VerifyConstructor(of = { "editable" })
@VerifyConstructor(of = { "value", "editable" })
class BaseDynamicFieldTest extends ValueObjectTest<StringEditableField> {

    @Test
    void shouldConstructIntegerField() {
        var editableField = new IntegerEditableField(true);
        assertEquals(DynamicFieldType.INTEGER, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue(5);
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());
        // Different constructor
        editableField = new IntegerEditableField(1, false);
    }

    @Test
    void shouldConstructStringField() {
        var editableField = new StringEditableField(true);
        assertEquals(DynamicFieldType.STRING, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue("5");
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());

        // Different constructor
        editableField = new StringEditableField("1", false);
    }

    @Test
    void shouldConstructBooleanField() {
        var editableField = new BooleanEditableField(true);
        assertEquals(DynamicFieldType.BOOLEAN, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue(Boolean.TRUE);
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());

        // Different constructor
        editableField = new BooleanEditableField(Boolean.TRUE, false);
    }

    @Test
    void shouldConstructDoubleField() {
        var editableField = new DoubleEditableField(true);
        assertEquals(DynamicFieldType.DOUBLE, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue(12.6664287277627762);
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());

        // Different constructor
        editableField = new DoubleEditableField(2d, false);
    }

    @Test
    void shouldConstructFloatField() {
        var editableField = new FloatEditableField(true);
        assertEquals(DynamicFieldType.FLOAT, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue(3.6f);
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());

        // Different constructor
        editableField = new FloatEditableField(1f, false);
    }

    @Test
    void shouldConstructLongField() {
        var editableField = new LongEditableField(true);
        assertEquals(DynamicFieldType.LONG, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
        editableField.setValue(12345678910L);
        assertTrue(editableField.isAvailable());
        assertTrue(editableField.isChanged());

        // Different constructor
        editableField = new LongEditableField(1L, false);
    }

    @Test
    void shouldEnterFalseValue() {
        final var editableField = new LongEditableField(true);
        editableField.setValue(null);
        assertEquals(DynamicFieldType.LONG, editableField.getFieldType());
        assertFalse(editableField.isAvailable());
    }

    @Test
    void shouldResetValue() {
        final var editableField = new LongEditableField(true);
        final var serialVersionUID = 7865845990018198224L;
        editableField.setValue(serialVersionUID);
        assertNull(editableField.resetValue());
    }

    @Test
    void shouldSetValue() {
        final var editableField = new LongEditableField(true);
        final var oldValue = 7865845990018198224L;
        final var newValue = 7865845990018198224L;
        editableField.setValue(oldValue);
        assertDoesNotThrow(() -> editableField.setValue(newValue));
    }

    @Test
    void shouldSetValue2() {
        final var editableField = new LongEditableField(true);
        final var oldValue = 7865845990018198224L;
        final var newValue = 7865845990018198324L;
        editableField.setValue(oldValue);
        assertDoesNotThrow(() -> editableField.setValue(newValue));
    }

    @Test
    void shouldThrowException() {
        final var editableField = new LongEditableField(false);
        final var serialVersionUID = 7865845990018198224L;
        assertThrows(IllegalStateException.class, () -> editableField.setValue(serialVersionUID));
    }

}
