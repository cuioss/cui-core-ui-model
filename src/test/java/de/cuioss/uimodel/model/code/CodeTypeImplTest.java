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
package de.cuioss.uimodel.model.code;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import de.cuioss.tools.property.PropertyReadWrite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests CodeType Implementation")
@PropertyConfig(name = CodeTypeImplTest.RESOLVED, propertyClass = String.class, propertyReadWrite = PropertyReadWrite.WRITE_ONLY)
@VerifyConstructor(of = CodeTypeImplTest.IDENTIFIER, required = CodeTypeImplTest.IDENTIFIER)
// Resolved is used as identifier
@VerifyConstructor(of = {CodeTypeImplTest.RESOLVED, CodeTypeImplTest.IDENTIFIER}, required = {
        CodeTypeImplTest.RESOLVED, CodeTypeImplTest.IDENTIFIER})
@ObjectTestConfig(equalsAndHashCodeOf = CodeTypeImplTest.IDENTIFIER)
class CodeTypeImplTest extends ValueObjectTest<CodeTypeImpl> {

    protected static final String RESOLVED = "resolved";
    protected static final String IDENTIFIER = "identifier";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should create with simple constructor")
        void shouldCreateWithSimpleConstructor() {
            // Arrange & Act
            final var type = new CodeTypeImpl(RESOLVED);

            // Assert
            assertEquals(RESOLVED, type.getResolved(Locale.ENGLISH));
            assertEquals(RESOLVED, type.getIdentifier());
        }

        @Test
        @DisplayName("Should create with standard constructor")
        void shouldCreateWithStandardConstructor() {
            // Arrange & Act
            final var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);

            // Assert
            assertEquals(RESOLVED, type.getResolved(Locale.ENGLISH));
            assertEquals(IDENTIFIER, type.getIdentifier());
        }
    }

    @Nested
    @DisplayName("Locale Handling Tests")
    class LocaleHandlingTests {

        @Test
        @DisplayName("Should ignore locale parameter")
        void shouldIgnoreLocale() {
            // Arrange
            final var type = new CodeTypeImpl(RESOLVED, IDENTIFIER);

            // Act & Assert
            assertEquals(RESOLVED, type.getResolved(null));
            assertEquals(IDENTIFIER, type.getIdentifier());
        }
    }
}
