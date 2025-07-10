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
package de.cuioss.uimodel.application;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyBuilder;
import de.cuioss.test.valueobjects.api.object.ObjectTestConfig;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static de.cuioss.test.generator.Generators.nonEmptyStrings;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Login Credentials Tests")
@VerifyBuilder
@PropertyReflectionConfig(of = {"username", "password", "userStore", "rememberLoginCredentials"})
@ObjectTestConfig(equalsAndHashCodeExclude = "password")
class LoginCredentialsTest extends ValueObjectTest<LoginCredentials> {

    @Nested
    @DisplayName("Completeness checks")
    class CompletenessTests {

        @ParameterizedTest
        @MethodSource
        @DisplayName("should detect completeness based on credentials")
        void shouldDetectCompleteness(LoginCredentials credentials, boolean expected) {
            assertEquals(expected, credentials.isComplete());
        }

        static Stream<Arguments> shouldDetectCompleteness() {
            return Stream.of(
                    arguments(
                            LoginCredentials.builder()
                                    .username(nonEmptyStrings().next())
                                    .password(nonEmptyStrings().next())
                                    .build(),
                            true
                    ),
                    arguments(
                            LoginCredentials.builder()
                                    .password(nonEmptyStrings().next())
                                    .build(),
                            false
                    ),
                    arguments(
                            LoginCredentials.builder()
                                    .username(nonEmptyStrings().next())
                                    .build(),
                            false
                    ),
                    arguments(
                            LoginCredentials.builder().build(),
                            false
                    )
            );
        }
    }
}
