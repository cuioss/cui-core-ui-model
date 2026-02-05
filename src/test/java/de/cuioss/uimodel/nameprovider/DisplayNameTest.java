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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests DisplayName Value Object")
@PropertyConfig(name = "content", propertyClass = String.class)
@VerifyConstructor(of = "content", required = "content")
class DisplayNameTest extends ValueObjectTest<de.cuioss.uimodel.nameprovider.DisplayName> {

    @Test
    @DisplayName("Should match Javadoc examples")
    void shouldMatchJavadocExamples() {
        // Basic usage example
        var name = new de.cuioss.uimodel.nameprovider.DisplayName("John Doe");
        assertEquals("John Doe", name.getContent(), "Basic content should match");

        // Collection usage example
        List<IDisplayNameProvider<String>> names = Arrays.asList(
                new de.cuioss.uimodel.nameprovider.DisplayName("First"),
                new de.cuioss.uimodel.nameprovider.DisplayName("Second")
        );
        assertEquals("First", names.getFirst().getContent(), "First item content should match");
        assertEquals("Second", names.get(1).getContent(), "Second item content should match");

        // Equality example
        var name1 = new de.cuioss.uimodel.nameprovider.DisplayName("Test");
        var name2 = new de.cuioss.uimodel.nameprovider.DisplayName("Test");
        assertEquals(name1, name2, "Objects with same content should be equal");
    }
}
