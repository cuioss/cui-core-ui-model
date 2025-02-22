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
package de.cuioss.uimodel.result;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ResultErrorCodes Tests")
class ResultErrorCodesTest {

    @Nested
    @DisplayName("HTTP Code Parsing Tests")
    class ParseHttpCodeTest {

        @ParameterizedTest(name = "HTTP code {0} should map to {1}")
        @CsvSource({
            "400, BAD_REQUEST",
            "401, NOT_AUTHENTICATED",
            "403, NOT_AUTHORIZED",
            "404, NOT_FOUND",
            "503, SERVICE_NOT_AVAILABLE",
            "500, RUNTIME_ERROR",
            "0, RUNTIME_ERROR"
        })
        void parseHttpCode(int httpCode, ResultErrorCodes expected) {
            // Act
            ResultErrorCodes actual = ResultErrorCodes.parseHttpCode(httpCode);

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Javadoc example tests")
    class JavadocExampleTests {
        
        @org.junit.jupiter.api.Test
        @DisplayName("Should demonstrate basic error code usage")
        void shouldDemonstrateBasicErrorCodeUsage() {
            // Given: A repository result
            var user = new TestUser("123", "John Doe");
            
            // When: User is not found
            var notFoundResult = findUser("456");
            
            // Then: Not found error is returned
            assertEquals(ResultErrorCodes.NOT_FOUND, notFoundResult.getErrorCode().get());
            assertEquals("User not found", notFoundResult.getResultDetail().get().getDetail().getContent());
            
            // When: Security exception occurs
            var securityResult = findUserWithSecurityException("123");
            
            // Then: Not authorized error is returned
            assertEquals(ResultErrorCodes.NOT_AUTHORIZED, securityResult.getErrorCode().get());
            assertEquals("Access denied", securityResult.getResultDetail().get().getDetail().getContent());
            
            // When: User is found
            var foundResult = findUser("123");
            
            // Then: Success result is returned
            assertTrue(foundResult.isValid());
            assertEquals(user.getId(), ((TestUser)foundResult.getResult()).getId());
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
                .errorCode(ResultErrorCodes.NOT_FOUND)
                .build();
        }
        
        private ResultObject<?> findUserWithSecurityException(String id) {
            return ResultObject.builder()
                .validDefaultResult(new TestUser("", ""))
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Access denied")))
                .errorCode(ResultErrorCodes.NOT_AUTHORIZED)
                .build();
        }
        
        private static class TestUser implements java.io.Serializable {
            private static final long serialVersionUID = 1L;
            private final String id;
            private final String name;
            
            TestUser(String id, String name) {
                this.id = id;
                this.name = name;
            }
            
            String getId() {
                return id;
            }
            
            String getName() {
                return name;
            }
        }
    }
}
