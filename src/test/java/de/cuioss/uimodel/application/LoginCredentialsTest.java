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
package de.cuioss.uimodel.application;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;

@VerifyBuilder
@PropertyReflectionConfig(of = { "username", "password", "userStore", "rememberLoginCredentials" })
@ObjectTestConfig(equalsAndHashCodeExclude = "password")
class LoginCredentialsTest extends ValueObjectTest<LoginCredentials> {

    @Test
    void shouldDetectCompletness() {
        assertTrue(LoginCredentials.builder().username(nonEmptyStrings().next()).password(nonEmptyStrings().next())
                .build().isComplete());
        assertFalse(LoginCredentials.builder().password(nonEmptyStrings().next()).build().isComplete());
        assertFalse(LoginCredentials.builder().username(nonEmptyStrings().next()).build().isComplete());
        assertFalse(LoginCredentials.builder().build().isComplete());
    }
}
