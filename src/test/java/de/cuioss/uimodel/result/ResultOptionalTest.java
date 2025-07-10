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
package de.cuioss.uimodel.result;

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("ResultOptional Tests")
class ResultOptionalTest {

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {
        @Test
        @DisplayName("should create empty optional result")
        void empty() {
            // Arrange & Act
            var result = new ResultOptional<String>(null, ResultState.VALID);

            // Assert
            assertTrue(result.isValid());
            assertFalse(result.getResult().isPresent());
            assertFalse(result.getResultDetail().isPresent());
            assertFalse(result.getErrorCode().isPresent());
            assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
        }

        @Test
        @DisplayName("should handle copy constructor for valid result")
        void shouldHandleCopyConstructorForValid() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
            var expected = resultBuilder.result("Test").state(ResultState.VALID).build();

            // Act
            var copy = new ResultOptional<>(expected, Function.identity());

            // Assert
            assertEquals(expected.getResult(), copy.getResult());
            assertEquals(expected.getErrorCode(), copy.getErrorCode());
            assertEquals(expected.getState(), copy.getState());
            assertEquals(expected.getResultDetail(), copy.getResultDetail());
        }

        @Test
        @DisplayName("should handle copy constructor for error result")
        void shouldHandleCopyConstructorForError() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
            var expected = resultBuilder.state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")))
                    .errorCode(ResultErrorCodes.NOT_FOUND).build();

            // Act
            var copy = new ResultOptional<>(expected, Function.identity());

            // Assert
            assertEquals(expected.getResult(), copy.getResult());
            assertEquals(expected.getErrorCode(), copy.getErrorCode());
            assertEquals(expected.getState(), copy.getState());
            assertEquals(expected.getResultDetail(), copy.getResultDetail());
        }
    }

    @Nested
    @DisplayName("Builder tests")
    class BuilderTests {
        @Test
        @DisplayName("should build valid result")
        void builder() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();

            // Act
            var result = resultBuilder.result("Test").state(ResultState.VALID).build();

            // Assert
            assertTrue(result.isValid());
            assertTrue(result.getResult().isPresent());
            assertEquals("Test", result.getResult().get());
            assertFalse(result.getResultDetail().isPresent());
            assertFalse(result.getErrorCode().isPresent());
            assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
        }

        @Test
        @DisplayName("should fail without state")
        void builderWithoutState() {
            // Arrange
            var resultBuilder = ResultOptional.optionalBuilder().result("Test");

            // Act & Assert
            assertThrows(UnsupportedOperationException.class, resultBuilder::build);
        }

        @Test
        @DisplayName("should fail with error state but without detail")
        void builderWithErrorAndWithoutDetail() {
            // Arrange
            var resultBuilder = ResultOptional.optionalBuilder().result("Test").state(ResultState.ERROR);

            // Act & Assert
            assertThrows(UnsupportedOperationException.class, resultBuilder::build);
        }

        @Test
        @DisplayName("should build result with detail and error code")
        void builderWithDetailAndErrorCode() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();

            // Act
            var result = resultBuilder.state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")))
                    .errorCode(ResultErrorCodes.NOT_FOUND).build();

            // Assert
            assertFalse(result.isValid());
            assertFalse(result.getResult().isPresent());
            assertTrue(result.getResultDetail().isPresent());
            assertEquals(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")),
                    result.getResultDetail().get());
            assertTrue(result.getErrorCode().isPresent());
            assertEquals(ResultErrorCodes.NOT_FOUND, result.getErrorCode().get());
            assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
        }

        @Test
        @DisplayName("should handle multiple result details")
        void builderWithTwoDetails() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();

            // Act
            var result = resultBuilder.state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")))
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")))
                    .build();

            // Assert
            assertFalse(result.isValid());
            assertFalse(result.getResult().isPresent());
            assertTrue(result.getResultDetail().isPresent());
            assertEquals(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")),
                    result.getResultDetail().get());
            assertFalse(result.getErrorCode().isPresent());
            assertEquals(result, SerializableContractImpl.serializeAndDeserialize(result));
        }

        @Test
        @DisplayName("should handle copy builder for valid result")
        void shouldHandleCopyBuilderForValid() {
            // Arrange
            ResultOptional.Builder<String> resultBuilder = ResultOptional.optionalBuilder();
            var expected = resultBuilder.result("Test").state(ResultState.VALID).build();

            // Act
            var copy = ResultOptional.optionalBuilder()
                    .extractStateAndDetailsAndErrorCodeFrom(expected)
                    .result("Test")
                    .build();

            // Assert
            assertEquals(expected.getResult(), copy.getResult());
            assertEquals(expected.getErrorCode(), copy.getErrorCode());
            assertEquals(expected.getState(), copy.getState());
            assertEquals(expected.getResultDetail(), copy.getResultDetail());
        }
    }

    @Nested
    @DisplayName("Javadoc example tests")
    class JavadocExampleTests {

        @Test
        @DisplayName("Should demonstrate basic optional result usage")
        void shouldDemonstrateBasicOptionalResult() {
            // Given: A service result
            var result = ResultOptional.<String>optionalBuilder()
                    .result("John Doe")
                    .state(ResultState.VALID)
                    .build();

            // When/Then: Result is present and can be processed
            assertTrue(result.isValid());
            result.getResult().ifPresent(name ->
                    assertEquals("John Doe", name)
            );
        }

        @Test
        @DisplayName("Should demonstrate builder pattern usage")
        void shouldDemonstrateBuilderPattern() {
            // When: Building a result with document
            var result = ResultOptional.<String>optionalBuilder()
                    .result("document content")
                    .state(ResultState.VALID)
                    .build();

            // Then: Result is valid and contains content
            assertTrue(result.isValid());
            assertTrue(result.getResult().isPresent());
            assertEquals("document content", result.getResult().get());
        }

        @Test
        @DisplayName("Should demonstrate result transformation")
        void shouldDemonstrateResultTransformation() {
            // Given: A user result
            var userResult = ResultOptional.<TestUser>optionalBuilder()
                    .result(new TestUser("John Doe"))
                    .state(ResultState.VALID)
                    .build();

            // When: Transforming to name result
            var nameResult = new ResultOptional<>(userResult, TestUser::name);

            // Then: Transformation preserves value and state
            assertTrue(nameResult.isValid());
            assertTrue(nameResult.getResult().isPresent());
            assertEquals("John Doe", nameResult.getResult().get());
        }

        @Test
        @DisplayName("Should demonstrate error handling")
        void shouldDemonstrateErrorHandling() {
            // Given: A service result with error
            var result = ResultOptional.<String>optionalBuilder()
                    .state(ResultState.ERROR)
                    .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Resource not found")))
                    .build();

            // When/Then: Error case
            assertFalse(result.isValid());
            assertTrue(result.getResultDetail().isPresent());
            assertEquals("Resource not found", result.getResultDetail().get().getDetail().getContent());

            // Given: A valid but empty result
            var emptyResult = ResultOptional.<String>optionalBuilder()
                    .result(null)
                    .state(ResultState.VALID)
                    .build();

            // When/Then: Empty case
            assertTrue(emptyResult.isValid());
            assertFalse(emptyResult.getResult().isPresent());
        }

        private record TestUser(String name) implements Serializable {
        }
    }
}
