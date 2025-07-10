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
package de.cuioss.uimodel.model.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Oliver Wolff FIXME owolff -> Replace with VerifyComparable
 */
@DisplayName("Tests CodeType Comparator")
class CodeTypeComparatorTest {

    @ParameterizedTest(name = "Compare ''{0}'' with ''{1}'' should return {2}")
    @DisplayName("Should compare code types correctly")
    @CsvSource({
            "'', '', 0",
            "1, '', 1",
            "'', 1, -1",
            "a, b, -1",
            "b, a, 1"
    })
    void shouldCompare(String first, String second, int expected) {
        // Arrange
        final var comparator = new CodeTypeComparator(Locale.ENGLISH);
        final var firstCode = new CodeTypeImpl(first);
        final var secondCode = new CodeTypeImpl(second);

        // Act & Assert
        assertEquals(expected, comparator.compare(firstCode, secondCode));
    }
}
