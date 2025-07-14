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
package de.cuioss.uimodel.model.conceptkey;

import de.cuioss.uimodel.model.conceptkey.impl.BaseConceptCategory;
import de.cuioss.uimodel.model.conceptkey.impl.ConceptKeyTypeImpl;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests AugmentationKeyConstants")
class AugmentationKeyConstantsTest {

    @Nested
    @DisplayName("Tests for isDefaultValue()")
    class DefaultValueTests {

        @Test
        @DisplayName("Should return false for null input")
        void shouldHandleNullInput() {
            assertFalse(AugmentationKeyConstants.isDefaultValue(null));
        }

        @Test
        @DisplayName("Should return false when default_value is not set")
        void shouldReturnFalseWhenNotSet() {
            // Given
            var conceptKey = createConceptKey(new HashMap<>());

            // Then
            assertFalse(AugmentationKeyConstants.isDefaultValue(conceptKey));
        }

        @ParameterizedTest
        @DisplayName("Should return true for various 'true' string representations")
        @ValueSource(strings = {"true", "TRUE", "True"})
        void shouldReturnTrueForTrueValues(String value) {
            // Given
            var augmentations = new HashMap<String, String>();
            augmentations.put(AugmentationKeyConstants.DEFAULT_VALUE, value);
            var conceptKey = createConceptKey(augmentations);

            // Then
            assertTrue(AugmentationKeyConstants.isDefaultValue(conceptKey));
        }

        @Test
        @DisplayName("Should return false for non-true value")
        void shouldReturnFalseForNonTrueValue() {
            // Given
            var augmentations = new HashMap<String, String>();
            augmentations.put(AugmentationKeyConstants.DEFAULT_VALUE, "false");
            var conceptKey = createConceptKey(augmentations);

            // Then
            assertFalse(AugmentationKeyConstants.isDefaultValue(conceptKey));
        }
    }

    @Nested
    @DisplayName("Tests for isUndefinedValue()")
    class UndefinedValueTests {

        @Test
        @DisplayName("Should return false for null input")
        void shouldHandleNullInput() {
            assertFalse(AugmentationKeyConstants.isUndefinedValue(null));
        }

        @Test
        @DisplayName("Should return false when undefined_value is not set")
        void shouldReturnFalseWhenNotSet() {
            // Given
            var conceptKey = createConceptKey(new HashMap<>());

            // Then
            assertFalse(AugmentationKeyConstants.isUndefinedValue(conceptKey));
        }

        @ParameterizedTest
        @DisplayName("Should return true for various 'true' string representations")
        @ValueSource(strings = {"true", "TRUE", "True"})
        void shouldReturnTrueForTrueValues(String value) {
            // Given
            var augmentations = new HashMap<String, String>();
            augmentations.put(AugmentationKeyConstants.UNDEFINED_VALUE, value);
            var conceptKey = createConceptKey(augmentations);

            // Then
            assertTrue(AugmentationKeyConstants.isUndefinedValue(conceptKey));
        }

        @Test
        @DisplayName("Should return false for non-true value")
        void shouldReturnFalseForNonTrueValue() {
            // Given
            var augmentations = new HashMap<String, String>();
            augmentations.put(AugmentationKeyConstants.UNDEFINED_VALUE, "false");
            var conceptKey = createConceptKey(augmentations);

            // Then
            assertFalse(AugmentationKeyConstants.isUndefinedValue(conceptKey));
        }
    }

    /**
     * Helper method to create a ConceptKeyType with given augmentations
     */
    private static ConceptKeyType createConceptKey(Map<String, String> augmentations) {
        return ConceptKeyTypeImpl.builder()
                .identifier("test")
                .category(new BaseConceptCategory("test"))
                .labelResolver(new I18nDisplayNameProvider("test"))
                .augmentation(augmentations)
                .build();
    }
}
