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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("DynamicFieldType Tests")
class DynamicFieldTypeTest {

    @Nested
    @DisplayName("Type Resolution Tests")
    class TypeResolutionTests {
        @ParameterizedTest(name = "Type {0} should resolve to {1}")
        @CsvSource({
                "notdefined, STRING",
                "java.lang.Object, STRING",
                "java.lang.String, STRING",
                "java.lang.Integer, INTEGER",
                "java.lang.Boolean, BOOLEAN",
                "java.lang.Double, DOUBLE",
                "java.lang.Float, FLOAT"
        })
        void shouldDetermineByClassName(String className, DynamicFieldType expected) {
            assertEquals(expected, DynamicFieldType.getByTypeString(className));
        }

        @ParameterizedTest(name = "Primitive type {0} should resolve to {1}")
        @CsvSource({
                "string, STRING",
                "int, INTEGER",
                "boolean, BOOLEAN",
                "double, DOUBLE",
                "float, FLOAT"
        })
        void shouldDetermineByPrimitiveName(String primitiveName, DynamicFieldType expected) {
            assertEquals(expected, DynamicFieldType.getByTypeString(expected.getPrimitiveName()));
        }
    }

    @Nested
    @DisplayName("Dynamic Field Creation Tests")
    class DynamicFieldCreationTests {

        @Test
        @DisplayName("Should create String field")
        void shouldCreateStringField() {
            // Arrange & Act
            DynamicField<? extends Serializable> stringField = DynamicFieldType.STRING.createDynamicField("value", true);

            // Assert
            assertNotNull(stringField);
            assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
            assertEquals("value", stringField.getValue());
            assertTrue(stringField.isEditable());
            assertTrue(stringField.isAvailable());

            // Arrange & Act
            stringField = DynamicFieldType.STRING.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
            assertNull(stringField.getValue());
            assertFalse(stringField.isEditable());
            assertFalse(stringField.isAvailable());
        }

        @Test
        @DisplayName("Should create Integer field")
        void shouldCreateIntegerField() {
            // Arrange & Act
            DynamicField<? extends Serializable> integerField = DynamicFieldType.INTEGER.createDynamicField(2, true);

            // Assert
            assertNotNull(integerField);
            assertEquals(DynamicFieldType.INTEGER, integerField.getFieldType());
            assertEquals(2, integerField.getValue());
            assertTrue(integerField.isEditable());
            assertTrue(integerField.isAvailable());

            // Arrange & Act
            integerField = DynamicFieldType.INTEGER.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.INTEGER, integerField.getFieldType());
            assertNull(integerField.getValue());
            assertFalse(integerField.isEditable());
            assertFalse(integerField.isAvailable());
        }

        @Test
        @DisplayName("Should create Boolean field")
        void shouldCreateBooleanField() {
            // Arrange & Act
            DynamicField<? extends Serializable> booleanField = DynamicFieldType.BOOLEAN.createDynamicField(Boolean.TRUE, true);

            // Assert
            assertNotNull(booleanField);
            assertEquals(DynamicFieldType.BOOLEAN, booleanField.getFieldType());
            assertEquals(Boolean.TRUE, booleanField.getValue());
            assertTrue(booleanField.isEditable());
            assertTrue(booleanField.isAvailable());

            // Arrange & Act
            booleanField = DynamicFieldType.BOOLEAN.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.BOOLEAN, booleanField.getFieldType());
            assertNull(booleanField.getValue());
            assertFalse(booleanField.isEditable());
            assertFalse(booleanField.isAvailable());
        }

        @Test
        @DisplayName("Should create Long field")
        void shouldCreateLongField() {
            // Arrange & Act
            DynamicField<? extends Serializable> longField = DynamicFieldType.LONG.createDynamicField(1L, true);

            // Assert
            assertNotNull(longField);
            assertEquals(DynamicFieldType.LONG, longField.getFieldType());
            assertEquals(1L, longField.getValue());
            assertTrue(longField.isEditable());
            assertTrue(longField.isAvailable());

            // Arrange & Act
            longField = DynamicFieldType.LONG.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.LONG, longField.getFieldType());
            assertNull(longField.getValue());
            assertFalse(longField.isEditable());
            assertFalse(longField.isAvailable());
        }

        @Test
        @DisplayName("Should create Double field")
        void shouldCreateDoubleField() {
            // Arrange & Act
            DynamicField<? extends Serializable> doubleField = DynamicFieldType.DOUBLE.createDynamicField(1d, true);

            // Assert
            assertNotNull(doubleField);
            assertEquals(DynamicFieldType.DOUBLE, doubleField.getFieldType());
            assertEquals(1d, doubleField.getValue());
            assertTrue(doubleField.isEditable());
            assertTrue(doubleField.isAvailable());

            // Arrange & Act
            doubleField = DynamicFieldType.DOUBLE.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.DOUBLE, doubleField.getFieldType());
            assertNull(doubleField.getValue());
            assertFalse(doubleField.isEditable());
            assertFalse(doubleField.isAvailable());
        }

        @Test
        @DisplayName("Should create Float field")
        void shouldCreateFloatField() {
            // Arrange & Act
            DynamicField<? extends Serializable> floatField = DynamicFieldType.FLOAT.createDynamicField(1f, true);

            // Assert
            assertNotNull(floatField);
            assertEquals(DynamicFieldType.FLOAT, floatField.getFieldType());
            assertEquals(1f, floatField.getValue());
            assertTrue(floatField.isEditable());
            assertTrue(floatField.isAvailable());

            // Arrange & Act
            floatField = DynamicFieldType.FLOAT.createDynamicField(null, false);

            // Assert
            assertEquals(DynamicFieldType.FLOAT, floatField.getFieldType());
            assertNull(floatField.getValue());
            assertFalse(floatField.isEditable());
            assertFalse(floatField.isAvailable());
        }
    }

    @Nested
    @DisplayName("Field Type Tests")
    class FieldTypeTests {
        @Test
        @DisplayName("Should identify boolean fields correctly")
        void shouldDetermineBooleanField() {
            assertTrue(DynamicFieldType.BOOLEAN.isBooleanField());
            assertFalse(DynamicFieldType.DOUBLE.isBooleanField());
            assertFalse(DynamicFieldType.FLOAT.isBooleanField());
            assertFalse(DynamicFieldType.INTEGER.isBooleanField());
            assertFalse(DynamicFieldType.LONG.isBooleanField());
            assertFalse(DynamicFieldType.STRING.isBooleanField());
        }
    }
}
