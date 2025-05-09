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
package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstants;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests BaseConceptCategory")
class BaseConceptCategoryTest {

    private static final String EXPLICIT_NAME = "test_category";

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor should use class name")
        void shouldUseClassNameAsDefaultName() {
            // Given
            var category = new BaseConceptCategory();

            // Then
            assertEquals("BaseConceptCategory", category.getName());
        }

        @Test
        @DisplayName("Explicit constructor should use given name")
        void shouldUseGivenName() {
            // Given
            var category = new BaseConceptCategory(EXPLICIT_NAME);

            // Then
            assertEquals(EXPLICIT_NAME, category.getName());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Categories with same name should be equal")
        void shouldBeEqualWithSameName() {
            // Given
            var category1 = new BaseConceptCategory(EXPLICIT_NAME);
            var category2 = new BaseConceptCategory(EXPLICIT_NAME);

            // Then
            assertEquals(category1, category2);
            assertEquals(category1.hashCode(), category2.hashCode());
        }

        @Test
        @DisplayName("Categories with different names should not be equal")
        void shouldNotBeEqualWithDifferentNames() {
            // Given
            var category1 = new BaseConceptCategory(EXPLICIT_NAME);
            var category2 = new BaseConceptCategory("other_category");

            // Then
            assertNotEquals(category1, category2);
            assertNotEquals(category1.hashCode(), category2.hashCode());
        }
    }

    @Nested
    @DisplayName("Undefined Concept Key Tests")
    class UndefinedConceptKeyTests {

        @Test
        @DisplayName("Should create undefined concept key with correct properties")
        void shouldCreateUndefinedConceptKey() {
            // Given
            var category = new BaseConceptCategory(EXPLICIT_NAME);
            var identifier = "test_identifier";

            // When
            ConceptKeyType undefinedKey = category.createUndefinedConceptKey(identifier);

            // Then
            assertNotNull(undefinedKey);
            assertEquals(identifier, undefinedKey.getIdentifier());
            assertEquals(category, undefinedKey.getCategory());
            assertTrue(Boolean.parseBoolean(undefinedKey.get(AugmentationKeyConstants.UNDEFINED_VALUE)));
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("ToString should contain name")
        void shouldIncludeNameInToString() {
            // Given
            var category = new BaseConceptCategory(EXPLICIT_NAME);

            // Then
            assertTrue(category.toString().contains(EXPLICIT_NAME));
        }
    }
}
