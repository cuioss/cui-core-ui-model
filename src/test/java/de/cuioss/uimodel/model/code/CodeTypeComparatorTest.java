package de.cuioss.uimodel.model.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * @author Oliver Wolff
 *         FIXME owolff -> Replace with VerifyComparable
 */
class CodeTypeComparatorTest {

    @Test
    void shouldCompare() {
        final var comparator = new CodeTypeComparator(Locale.ENGLISH);

        assertEquals(0, comparator.compare(new CodeTypeImpl(""), new CodeTypeImpl("")));
        assertEquals(1, comparator.compare(new CodeTypeImpl("1"), new CodeTypeImpl("")));
        assertEquals(-1, comparator.compare(new CodeTypeImpl(""), new CodeTypeImpl("1")));
    }

}
