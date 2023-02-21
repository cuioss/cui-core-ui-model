package io.cui.core.uimodel.model;

import static io.cui.test.generator.Generators.integers;
import static io.cui.test.generator.Generators.strings;
import static io.cui.tools.collect.CollectionLiterals.mutableList;
import static java.lang.Integer.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.cui.test.generator.junit.EnableGeneratorController;

@EnableGeneratorController
class ControlledCollectionProviderTest {

    private ControlledCollectionProvider<?> underTest;

    @Test
    void shouldRespectInitialSizeLimitation() {

        final Integer initialLimitation = 10;
        underTest = createAny(initialLimitation);

        assertThat("Initial limitation does not match",
                valueOf(underTest.getContent().size()), is(initialLimitation));
    }

    @Test
    void shouldProvidePossibilityToRemoveRestriction() {
        final Integer initialLimitation = 10;
        underTest = createAny(initialLimitation);

        assertThat("Initial limitation does not match",
                valueOf(underTest.getContent().size()), is(initialLimitation));

        underTest.removeRestriction();

        assertThat("Initial limitation still active",
                valueOf(underTest.getContent().size()), is(not(initialLimitation)));
    }

    @Test
    void shouldRecognizeIfNoNeedToRestrict() {
        final Integer single = 1;
        final Integer initialLimitation = 10;
        underTest =
            new ControlledCollectionProvider<>(mutableList("single element"), initialLimitation);

        assertThat("Initial limitation still active",
                valueOf(underTest.getContent().size()), is(single));

        underTest.removeRestriction();

        assertThat("Initial limitation still active",
                valueOf(underTest.getContent().size()), is(single));
    }

    @Test
    void shouldProvideNonNullEmptyInstance() {
        assertNotNull(ControlledCollectionProvider.emptyInstance());
    }

    private static ControlledCollectionProvider<String> createAny(final int initialSizeLimitation) {
        final var source = createAnyHugeCollection(initialSizeLimitation);
        return new ControlledCollectionProvider<>(source, initialSizeLimitation);
    }

    private static List<String> createAnyHugeCollection(final int minSize) {
        final int count = integers(minSize + 1, 50).next();
        final List<String> result = new ArrayList<>(count);
        for (var i = 0; i < count; i++) {
            result.add(strings().next());
        }
        return result;
    }

}
