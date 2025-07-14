/*
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.field.impl;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.uimodel.field.DynamicFieldType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Base Dynamic Field Tests")
@VerifyConstructor(of = {"editable"})
@VerifyConstructor(of = {"value", "editable"})
class BaseDynamicFieldTest extends ValueObjectTest<StringEditableField> {

    @Nested
    @DisplayName("Field Type Construction Tests")
    class FieldTypeConstructionTests {

        @Test
        @DisplayName("Should construct Integer field")
        void shouldConstructIntegerField() {
            // Arrange & Act
            var editableField = new IntegerEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.INTEGER, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue(5);

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new IntegerEditableField(1, false));
        }

        @Test
        @DisplayName("Should construct String field")
        void shouldConstructStringField() {
            // Arrange & Act
            var editableField = new StringEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.STRING, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue("5");

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new StringEditableField("1", false));
        }

        @Test
        @DisplayName("Should construct Boolean field")
        void shouldConstructBooleanField() {
            // Arrange & Act
            var editableField = new BooleanEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.BOOLEAN, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue(Boolean.TRUE);

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new BooleanEditableField(Boolean.TRUE, false));
        }

        @Test
        @DisplayName("Should construct Double field")
        void shouldConstructDoubleField() {
            // Arrange & Act
            var editableField = new DoubleEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.DOUBLE, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue(12.6664287277627762);

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new DoubleEditableField(2d, false));
        }

        @Test
        @DisplayName("Should construct Float field")
        void shouldConstructFloatField() {
            // Arrange & Act
            var editableField = new FloatEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.FLOAT, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue(3.6f);

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new FloatEditableField(1f, false));
        }

        @Test
        @DisplayName("Should construct Long field")
        void shouldConstructLongField() {
            // Arrange & Act
            var editableField = new LongEditableField(true);

            // Assert
            assertEquals(DynamicFieldType.LONG, editableField.getFieldType());
            assertFalse(editableField.isAvailable());

            // Act
            editableField.setValue(12345678910L);

            // Assert
            assertTrue(editableField.isAvailable());
            assertTrue(editableField.isChanged());

            // Assert alternative constructor
            assertDoesNotThrow(() -> new LongEditableField(1L, false));
        }
    }

    @Nested
    @DisplayName("Value Management Tests")
    class ValueManagementTests {

        @Test
        @DisplayName("Should handle null value")
        void shouldEnterFalseValue() {
            // Arrange
            final var editableField = new LongEditableField(true);

            // Act
            editableField.setValue(null);

            // Assert
            assertEquals(DynamicFieldType.LONG, editableField.getFieldType());
            assertFalse(editableField.isAvailable());
        }

        @Test
        @DisplayName("Should reset value")
        void shouldResetValue() {
            // Arrange
            final var editableField = new LongEditableField(true);
            final var serialVersionUID = 7865845990018198224L;

            // Act
            editableField.setValue(serialVersionUID);

            // Assert
            assertNull(editableField.resetValue());
        }

        @Test
        @DisplayName("Should set same value")
        void shouldSetValue() {
            // Arrange
            final var editableField = new LongEditableField(true);
            final var oldValue = 7865845990018198224L;
            final var newValue = 7865845990018198224L;

            // Act
            editableField.setValue(oldValue);

            // Assert
            assertDoesNotThrow(() -> editableField.setValue(newValue));
        }

        @Test
        @DisplayName("Should set different value")
        void shouldSetValue2() {
            // Arrange
            final var editableField = new LongEditableField(true);
            final var oldValue = 7865845990018198224L;
            final var newValue = 7865845990018198324L;

            // Act
            editableField.setValue(oldValue);

            // Assert
            assertDoesNotThrow(() -> editableField.setValue(newValue));
        }

        @Test
        @DisplayName("Should throw exception for non-editable field")
        void shouldThrowException() {
            // Arrange
            final var editableField = new LongEditableField(false);
            final var serialVersionUID = 7865845990018198224L;

            // Act & Assert
            assertThrows(IllegalStateException.class, () -> editableField.setValue(serialVersionUID));
        }
    }
}
