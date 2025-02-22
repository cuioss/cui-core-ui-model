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

import de.cuioss.test.valueobjects.contract.SerializableContractImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@org.junit.jupiter.api.DisplayName("ResultOptional Tests")
class ResultOptionalTest {

    @Nested
    @org.junit.jupiter.api.DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {
        @Test
        @org.junit.jupiter.api.DisplayName("should create empty optional result")
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
        @org.junit.jupiter.api.DisplayName("should handle copy constructor for valid result")
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
        @org.junit.jupiter.api.DisplayName("should handle copy constructor for error result")
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
    @org.junit.jupiter.api.DisplayName("Builder tests")
    class BuilderTests {
        @Test
        @org.junit.jupiter.api.DisplayName("should build valid result")
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
        @org.junit.jupiter.api.DisplayName("should fail without state")
        void builderWithoutState() {
            // Arrange
            var resultBuilder = ResultOptional.optionalBuilder().result("Test");

            // Act & Assert
            assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
        }

        @Test
        @org.junit.jupiter.api.DisplayName("should fail with error state but without detail")
        void builderWithErrorAndWithoutDetail() {
            // Arrange
            var resultBuilder = ResultOptional.optionalBuilder().result("Test").state(ResultState.ERROR);

            // Act & Assert
            assertThrows(UnsupportedOperationException.class, () -> resultBuilder.build());
        }

        @Test
        @org.junit.jupiter.api.DisplayName("should build result with detail and error code")
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
        @org.junit.jupiter.api.DisplayName("should handle multiple result details")
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
        @org.junit.jupiter.api.DisplayName("should handle copy builder for valid result")
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
}
