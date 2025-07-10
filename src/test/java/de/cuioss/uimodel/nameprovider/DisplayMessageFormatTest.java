/**
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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static de.cuioss.test.generator.Generators.integerObjects;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static de.cuioss.test.generator.Generators.strings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests DisplayMessageFormat Implementation")
@PropertyReflectionConfig(required = "msgKey")
// FIXME efischer: The structure seems to be problematic
// @PropertyBuilderConfig(name = "arguments", builderMethodName = "addAll")
// @VerifyBuilder(of = { "msgKey", "arguments" })
@VerifyConstructor(of = {"msgKey", "arguments"}, required = {"msgKey", "arguments"})
@EnableTestLogger
class DisplayMessageFormatTest extends ValueObjectTest<DisplayMessageFormat> {

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        @Test
        @DisplayName("Should fail on empty builder")
        void shouldFailOnEmptyBuilder() {
            // Arrange
            var builder = DisplayMessageFormat.builder();

            // Act & Assert
            assertThrows(NullPointerException.class, builder::build);
        }

        @Test
        @DisplayName("Should warn on missing arguments")
        void shouldWarnOnMissingArguments() {
            // Arrange
            var builder = DisplayMessageFormat.builder().msgKey(nonEmptyStrings().next());

            // Act
            builder.build();

            // Assert
            LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN, "No message format arguments provided");
        }
    }

    @Nested
    @DisplayName("Argument Handling Tests")
    class ArgumentTests {

        @Test
        @DisplayName("Should handle various argument scenarios")
        void shouldHandleArguments() {
            // Arrange
            var builder = new DisplayMessageFormat.Builder(nonEmptyStrings().next());
            var ints = Generators.integerObjects();

            // Act
            builder.add((Serializable[]) null);
            builder.add();
            builder.add(ints.next());
            builder.add(ints.next(), ints.next());
            builder.addAll(immutableList(ints.next(), ints.next()));
            builder.addAll(null);
            var result = builder.build();

            // Assert
            assertEquals(5, result.getContent().getArguments().size());
        }

        @Test
        @DisplayName("Should handle varargs constructor")
        void shouldHandleVarArgsConstructor() {
            // Act & Assert
            assertDoesNotThrow(
                    () -> new DisplayMessageFormat(nonEmptyStrings().next(), integerObjects().next(), strings().next()));
        }
    }
}
