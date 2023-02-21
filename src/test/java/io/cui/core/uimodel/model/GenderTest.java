package io.cui.core.uimodel.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.Generators;
import io.cui.test.generator.junit.EnableGeneratorController;
import lombok.RequiredArgsConstructor;

@EnableGeneratorController
class GenderTest {

    private static final Collection<Entry<String, Gender>> TEST_VALUES = initTestValues();

    @Test
    final void shouldDetermineEnumFromString() {
        final var testItem = anyTestItem();

        final var genderString = testItem.getKey();
        final var expected = testItem.getValue();
        assertThat(Gender.determineFromString(genderString), is(expected));
    }

    @Test
    final void shouldHandleUndifinedValues() {
        final var expected = Gender.UNKNOWN;
        assertThat(Gender.determineFromString(null), is(expected));
        assertThat(Gender.determineFromString(""), is(expected));
        assertThat(Gender.determineFromString("blablabla"), is(expected));
    }

    @Test
    final void shouldProvideMsgKeyAndCssInfo() {
        for (final Gender entry : Gender.values()) {
            assertThat(entry + " has no valid css info", entry.getCssClass(), is(notNullValue()));
            assertThat(entry + " has no valid title info", entry.getLabelKey(), is(notNullValue()));
        }
    }

    @Test
    final void shouldBeLabelKeyProvider() {
        assertTrue(Gender.MALE instanceof LabelKeyProvider);
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
