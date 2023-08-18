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
package de.cuioss.uimodel.nameprovider;

import static de.cuioss.test.generator.Generators.integerObjects;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static de.cuioss.test.generator.Generators.strings;
import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@PropertyReflectionConfig(required = "msgKey")
// FIXME efischer: The structure seems to be problematic
// @PropertyBuilderConfig(name = "arguments", builderMethodName = "addAll")
// @VerifyBuilder(of = { "msgKey", "arguments" })
@VerifyConstructor(of = { "msgKey", "arguments" }, required = { "msgKey", "arguments" })
@EnableTestLogger
class DisplayMessageFormatTest extends ValueObjectTest<DisplayMessageFormat> {

    @Test
    void shouldFailOnEmptyBuilder() {
        var builder = DisplayMessageFormat.builder();
        assertThrows(NullPointerException.class, () -> builder.build());
    }

    @Test
    void shouldWarnOnMissingArguments() {
        var builder = DisplayMessageFormat.builder().msgKey(nonEmptyStrings().next());
        builder.build();
        LogAsserts.assertSingleLogMessagePresentContaining(TestLogLevel.WARN, "LabeledKey instead?");
    }

    @Test
    void shouldHandleArguments() {
        var builder = new DisplayMessageFormat.Builder(nonEmptyStrings().next());
        var ints = Generators.integerObjects();
        builder.add((Serializable[]) null);
        builder.add();
        builder.add(ints.next());
        builder.add(ints.next(), ints.next());
        builder.addAll(immutableList(ints.next(), ints.next()));
        builder.addAll(null);
        var result = builder.build();
        assertEquals(5, result.getContent().getArguments().size());
    }

    @Test
    void shhouldHandleVarArgsConstuctor() {
        assertDoesNotThrow(
                () -> new DisplayMessageFormat(nonEmptyStrings().next(), integerObjects().next(), strings().next()));
    }
}
