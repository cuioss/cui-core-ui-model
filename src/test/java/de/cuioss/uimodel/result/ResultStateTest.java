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
package de.cuioss.uimodel.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("ResultState Tests")
class ResultStateTest {

    @Nested
    @DisplayName("Basic state tests")
    class BasicStateTests {

        @Test
        @DisplayName("should identify valid state")
        void shouldIdentifyValidState() {
            var validResult = ResultObject.builder()
                    .result("test")
                    .state(ResultState.VALID)
                    .build();
            assertEquals(ResultState.VALID, validResult.getState());

            var errorResult = ResultObject.builder()
                    .validDefaultResult("")
                    .state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Error")))
                    .build();
            assertEquals(ResultState.ERROR, errorResult.getState());

            var warningResult = ResultObject.builder()
                    .validDefaultResult("")
                    .state(ResultState.WARNING)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Warning")))
                    .build();
            assertEquals(ResultState.WARNING, warningResult.getState());

            var infoResult = ResultObject.builder()
                    .validDefaultResult("")
                    .state(ResultState.INFO)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Info")))
                    .build();
            assertEquals(ResultState.INFO, infoResult.getState());
        }
    }

    @Nested
    @DisplayName("Javadoc example tests")
    class JavadocExampleTests {

        @Test
        @DisplayName("Should demonstrate basic state handling")
        void shouldDemonstrateBasicStateHandling() {
            // Given: A user service result
            var validResult = findUser("123");
            var errorResult = findUser("456");

            // When/Then: Valid state handling
            if (validResult.getState() == ResultState.VALID) {
                var user = (TestUser) validResult.getResult();
                assertEquals("123", user.id());
                assertEquals("John Doe", user.name());
            }

            // When/Then: Error state handling
            if (errorResult.getState() == ResultState.ERROR) {
                assertEquals("User not found",
                        errorResult.getResultDetail().get().getDetail().getContent());
            }
        }

        @Test
        @DisplayName("Should demonstrate state-based flow control")
        void shouldDemonstrateStateBasedFlowControl() {
            // Given: Different result states
            var validResult = findUser("123");
            var errorResult = findUser("456");
            var warningResult = findUserWithWarning();
            var infoResult = findUserWithInfo();

            // When/Then: State-based handling
            switch (validResult.getState()) {
                case VALID:
                    var user = (TestUser) validResult.getResult();
                    assertEquals("123", user.id());
                    break;
                case ERROR:
                    fail("Should not reach error state");
                    break;
                default:
                    fail("Should not reach default state");
            }

            if (Objects.requireNonNull(errorResult.getState()) == ResultState.ERROR) {
                assertTrue(errorResult.getResultDetail().isPresent());
                assertEquals("User not found",
                        errorResult.getResultDetail().get().getDetail().getContent());
            } else {
                fail("Should not reach default state");
            }

            if (Objects.requireNonNull(warningResult.getState()) == ResultState.WARNING) {
                assertTrue(warningResult.getResultDetail().isPresent());
                assertEquals("User account will expire soon",
                        warningResult.getResultDetail().get().getDetail().getContent());
            } else {
                fail("Should not reach default state");
            }

            if (Objects.requireNonNull(infoResult.getState()) == ResultState.INFO) {
                assertTrue(infoResult.getResultDetail().isPresent());
                assertEquals("User last login: yesterday",
                        infoResult.getResultDetail().get().getDetail().getContent());
            } else {
                fail("Should not reach default state");
            }
        }

        private ResultObject<?> findUser(String id) {
            if ("123".equals(id)) {
                return ResultObject.builder()
                        .result(new TestUser("123", "John Doe"))
                        .state(ResultState.VALID)
                        .build();
            }
            return ResultObject.builder()
                    .validDefaultResult(new TestUser("", ""))
                    .state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("User not found")))
                    .build();
        }

        private ResultObject<?> findUserWithWarning() {
            return ResultObject.builder()
                    .validDefaultResult(new TestUser("", ""))
                    .state(ResultState.WARNING)
                    .resultDetail(new ResultDetail(
                            new de.cuioss.uimodel.nameprovider.DisplayName("User account will expire soon")))
                    .build();
        }

        private ResultObject<?> findUserWithInfo() {
            return ResultObject.builder()
                    .validDefaultResult(new TestUser("", ""))
                    .state(ResultState.INFO)
                    .resultDetail(new ResultDetail(
                            new de.cuioss.uimodel.nameprovider.DisplayName("User last login: yesterday")))
                    .build();
        }

        private record TestUser(String id, String name) implements Serializable {

        }
    }
}
