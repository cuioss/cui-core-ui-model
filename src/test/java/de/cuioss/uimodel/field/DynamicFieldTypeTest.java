/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

class DynamicFieldTypeTest {

    @Test
    void shouldDetermineByType() {
        assertEquals(DynamicFieldType.STRING, DynamicFieldType.getByTypeString("notdefined"));
        assertEquals(DynamicFieldType.STRING, DynamicFieldType.getByTypeString(Object.class.getName()));

        assertEquals(DynamicFieldType.STRING, DynamicFieldType.getByTypeString(String.class.getName()));
        assertEquals(DynamicFieldType.STRING,
                DynamicFieldType.getByTypeString(DynamicFieldType.STRING.getPrimitiveName()));

        assertEquals(DynamicFieldType.INTEGER, DynamicFieldType.getByTypeString(Integer.class.getName()));
        assertEquals(DynamicFieldType.INTEGER,
                DynamicFieldType.getByTypeString(DynamicFieldType.INTEGER.getPrimitiveName()));

        assertEquals(DynamicFieldType.BOOLEAN, DynamicFieldType.getByTypeString(Boolean.class.getName()));
        assertEquals(DynamicFieldType.BOOLEAN,
                DynamicFieldType.getByTypeString(DynamicFieldType.BOOLEAN.getPrimitiveName()));

        assertEquals(DynamicFieldType.DOUBLE, DynamicFieldType.getByTypeString(Double.class.getName()));
        assertEquals(DynamicFieldType.DOUBLE,
                DynamicFieldType.getByTypeString(DynamicFieldType.DOUBLE.getPrimitiveName()));

        assertEquals(DynamicFieldType.FLOAT, DynamicFieldType.getByTypeString(Float.class.getName()));
        assertEquals(DynamicFieldType.FLOAT,
                DynamicFieldType.getByTypeString(DynamicFieldType.FLOAT.getPrimitiveName()));
    }

    @Test
    void shouldCreateStringField() {
        DynamicField<? extends Serializable> stringField = DynamicFieldType.STRING.createDynamicField("value", true);
        assertNotNull(stringField);
        assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
        assertEquals("value", stringField.getValue());

        assertTrue(stringField.isEditable());
        assertTrue(stringField.isAvailable());

        stringField = DynamicFieldType.STRING.createDynamicField(null, false);

        assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
        assertNull(stringField.getValue());

        assertFalse(stringField.isEditable());
        assertFalse(stringField.isAvailable());
    }

    @Test
    void shouldCreateIntegerField() {
        DynamicField<? extends Serializable> integerField = DynamicFieldType.INTEGER.createDynamicField(2, true);
        assertNotNull(integerField);
        assertEquals(DynamicFieldType.INTEGER, integerField.getFieldType());
        assertEquals(2, integerField.getValue());

        assertTrue(integerField.isEditable());
        assertTrue(integerField.isAvailable());

        integerField = DynamicFieldType.INTEGER.createDynamicField(null, false);

        assertEquals(DynamicFieldType.INTEGER, integerField.getFieldType());
        assertNull(integerField.getValue());

        assertFalse(integerField.isEditable());
        assertFalse(integerField.isAvailable());
    }

    @Test
    void shouldCreateBooleanField() {
        DynamicField<? extends Serializable> booleanField = DynamicFieldType.BOOLEAN.createDynamicField(Boolean.TRUE,
                true);
        assertNotNull(booleanField);
        assertEquals(DynamicFieldType.BOOLEAN, booleanField.getFieldType());
        assertEquals(Boolean.TRUE, booleanField.getValue());

        assertTrue(booleanField.isEditable());
        assertTrue(booleanField.isAvailable());

        booleanField = DynamicFieldType.BOOLEAN.createDynamicField(null, false);

        assertEquals(DynamicFieldType.BOOLEAN, booleanField.getFieldType());
        assertNull(booleanField.getValue());

        assertFalse(booleanField.isEditable());
        assertFalse(booleanField.isAvailable());
    }

    @Test
    void shouldCreateLongField() {
        DynamicField<? extends Serializable> booleanField = DynamicFieldType.LONG.createDynamicField(1L, true);
        assertNotNull(booleanField);
        assertEquals(DynamicFieldType.LONG, booleanField.getFieldType());
        assertEquals(1L, booleanField.getValue());

        assertTrue(booleanField.isEditable());
        assertTrue(booleanField.isAvailable());

        booleanField = DynamicFieldType.LONG.createDynamicField(null, false);

        assertEquals(DynamicFieldType.LONG, booleanField.getFieldType());
        assertNull(booleanField.getValue());

        assertFalse(booleanField.isEditable());
        assertFalse(booleanField.isAvailable());
    }

    @Test
    void shouldCreateDoubleField() {
        DynamicField<? extends Serializable> doubleField = DynamicFieldType.DOUBLE.createDynamicField(1d, true);

        assertNotNull(doubleField);
        assertEquals(DynamicFieldType.DOUBLE, doubleField.getFieldType());
        assertEquals(1d, doubleField.getValue());

        assertTrue(doubleField.isEditable());
        assertTrue(doubleField.isAvailable());

        doubleField = DynamicFieldType.DOUBLE.createDynamicField(null, false);

        assertEquals(DynamicFieldType.DOUBLE, doubleField.getFieldType());
        assertNull(doubleField.getValue());

        assertFalse(doubleField.isEditable());
        assertFalse(doubleField.isAvailable());
    }

    @Test
    void shouldCreateFloatField() {
        DynamicField<? extends Serializable> floatField = DynamicFieldType.FLOAT.createDynamicField(1f, true);

        assertNotNull(floatField);
        assertEquals(DynamicFieldType.FLOAT, floatField.getFieldType());
        assertEquals(1f, floatField.getValue());

        assertTrue(floatField.isEditable());
        assertTrue(floatField.isAvailable());

        floatField = DynamicFieldType.FLOAT.createDynamicField(null, false);

        assertEquals(DynamicFieldType.FLOAT, floatField.getFieldType());
        assertNull(floatField.getValue());

        assertFalse(floatField.isEditable());
        assertFalse(floatField.isAvailable());
    }

    @Test
    void shouldDetermineBooleanField() {
        assertTrue(DynamicFieldType.BOOLEAN.isBooleanField());
        assertFalse(DynamicFieldType.DOUBLE.isBooleanField());

        assertFalse(DynamicFieldType.FLOAT.isBooleanField());
        assertFalse(DynamicFieldType.INTEGER.isBooleanField());
        assertFalse(DynamicFieldType.LONG.isBooleanField());
        assertFalse(DynamicFieldType.STRING.isBooleanField());
    }

}
