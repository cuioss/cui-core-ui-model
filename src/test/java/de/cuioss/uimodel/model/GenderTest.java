package de.cuioss.uimodel.model;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.junit.EnableGeneratorController;
import lombok.RequiredArgsConstructor;

@EnableGeneratorController
class GenderTest {

    private static final Collection<Entry<String, Gender>> TEST_VALUES = initTestValues();

    @Test
    final void shouldDetermineEnumFromString() {
        final var testItem = anyTestItem();

        final var genderString = testItem.getKey();
        final var expected = testItem.getValue();
        assertEquals(expected, Gender.fromString(genderString));
    }

    @Test
    final void shouldHandleUndifinedValues() {
        final var expected = Gender.UNKNOWN;
        assertEquals(expected, Gender.fromString(null));
        assertEquals(expected, Gender.fromString(""));
        assertEquals(expected, Gender.fromString(nonEmptyStrings().next()));
    }

    @Test
    final void shouldProvideMsgKey() {
        for (final Gender entry : Gender.values()) {
            assertNotNull(entry.getLabelKey(), entry + " has no valid title info");
        }
    }

    @Test
    final void shouldBeLabelKeyProvider() {
        assertEquals("cui.model.gender.male.title", Gender.MALE.getLabelKey());
    }

    private static Entry<String, Gender> anyTestItem() {
        return Generators.fixedValues(TEST_VALUES).next();
    }

    private static Collection<Entry<String, Gender>> initTestValues() {
        final Set<Entry<String, Gender>> result = new HashSet<>();
        result.add(new TestEntry("m", Gender.MALE));
        result.add(new TestEntry("male", Gender.MALE));
        result.add(new TestEntry("f", Gender.FEMALE));
        result.add(new TestEntry("female", Gender.FEMALE));
        result.add(new TestEntry("o", Gender.OTHER));
        result.add(new TestEntry("other", Gender.OTHER));
        result.add(new TestEntry("d", Gender.DIVERSE));
        result.add(new TestEntry("diverse", Gender.DIVERSE));
        result.add(new TestEntry("x", Gender.UNDEFINED));
        result.add(new TestEntry("undefined", Gender.UNDEFINED));
        return result;
    }

    @RequiredArgsConstructor
    private static class TestEntry implements java.util.Map.Entry<String, Gender> {

        private final String key;

        private final Gender value;

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Gender getValue() {
            return value;
        }

        @Override
        public Gender setValue(final Gender value) {
            throw new UnsupportedOperationException("not supported");
        }

    }

}
