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
