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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.juli.LogAsserts;
import de.cuioss.test.juli.TestLogLevel;
import de.cuioss.test.juli.junit5.EnableTestLogger;
import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.nameprovider.LabeledKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Function;

import static de.cuioss.uimodel.result.ResultState.VALID;
import static de.cuioss.uimodel.result.ResultState.WARNING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@PropertyReflectionConfig(skip = true)
@EnableTestLogger
@DisplayName("ResultObject Tests")
class ResultObjectTest extends ValueObjectTest<ResultObject<?>> {

    private static final CuiLogger log = new CuiLogger(ResultObjectTest.class);

    private static final ResultObject<String> SERVICE_NOT_AVAILABLE = new ResultObject<>("Test", ResultState.ERROR,
            new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")), ExampleErrorCodes.SERVICE_NOT_AVAILABLE);

    private final ResultDetailGenerator detailGenerator = new ResultDetailGenerator();

    private final ResultObjectGenerator generator = new ResultObjectGenerator();

    @Override
    protected ResultObject<?> anyValueObject() {
        return generator.next();
    }

    @Nested
    @DisplayName("Basic functionality tests")
    class BasicFunctionalityTests {
        @Test
        @DisplayName("should provide result and validate constructor parameters")
        void shouldProvideResult() {
            // Arrange
            final ResultObject<?> target = generator.next();
            var resultState = Generators.enumValues(ResultState.class).next();
            var detail = detailGenerator.next();

            // Act & Assert
            assertNotNull(target.getResult());
            assertThrows(IllegalArgumentException.class, () -> new ResultObject<Serializable>(null, resultState, detail));
        }

        @Test
        @DisplayName("should validate state parameter")
        void shouldProvideState() {
            // Arrange
            var result = Generators.nonEmptyStrings().next();

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> new ResultObject<>(result, ResultState.ERROR));
        }

        @Test
        @DisplayName("should allow optional result detail")
        void shouldAllowOptionalResultDetail() {
            // Arrange & Act
            final ResultObject<?> target = new ResultObject<>(Generators.nonEmptyStrings().next(), VALID);

            // Assert
            assertThat(target.getResultDetail().isPresent(), is(Boolean.FALSE));
        }

        @Test
        @DisplayName("should handle copy constructor with mapper")
        @SuppressWarnings("unchecked")
        void shouldHandleCopyConstructorWithMapper() {
            // Arrange
            final ResultObject<String> expected = generator.next();

            // Act
            var copy = new ResultObject<>(expected, Function.identity(), "");

            // Assert
            assertEquals(expected.getResult(), copy.getResult());
        }

        @Test
        @DisplayName("should handle copy constructor")
        @SuppressWarnings("unchecked")
        void shouldHandleCopyConstructor() {
            // Arrange
            final ResultObject<String> expected = generator.next();

            // Act
            var copy = new ResultObject<>(expected.getResult(), expected);

            // Assert
            assertEquals(expected.getResult(), copy.getResult());
            assertEquals(expected.getErrorCode(), copy.getErrorCode());
            assertEquals(expected.getState(), copy.getState());
            assertEquals(expected.getResultDetail(), copy.getResultDetail());
        }

        @Test
        @DisplayName("should prevent ignore error")
        void shouldPreventIgnoreError() {
            // Arrange
            final ResultObject<?> target = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.ERROR,
                    detailGenerator.next());

            // Act & Assert
            assertThrows(UnsupportedOperationException.class, target::getResult);
            assertThat(target.getResultDetail(), is(notNullValue()));
            assertThat(target.getResult(), is(notNullValue()));
        }
    }

    @Nested
    @DisplayName("Builder tests")
    class BuilderTests {
        @Test
        @DisplayName("should support valid result")
        void builderShouldSupportValidResult() {
            // Arrange
            final var builder = new ResultObject.Builder<Serializable>();
            final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                    detailGenerator.next());

            // Act
            builder.validDefaultResult(anyResult).state(VALID);
            final var result = builder.build();

            // Assert
            assertTrue(result.isValid());
        }

        @Test
        @DisplayName("should fail with invalid state")
        void builderShouldFailWithInValidState() {
            // Arrange
            final var builder = new ResultObject.Builder<Serializable>();
            final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                    detailGenerator.next());

            // Act
            builder.validDefaultResult(anyResult);

            // Assert
            assertThrows(UnsupportedOperationException.class, builder::build);
        }

        @Test
        @DisplayName("should support API user")
        void builderShouldSupportApiUser() {
            // Arrange
            final var builder = new ResultObject.Builder<Serializable>();

            // Act & Assert
            var exception = assertThrows(UnsupportedOperationException.class, builder::build);
            assertThat(exception.getMessage(), containsString("Use setResult or setValidDefaultResult as fallback"));
        }

        @Test
        @DisplayName("should verify parameter on reuse previous result")
        void shouldVerifyParameterOnReusePreviousResult() {
            // Arrange
            final var builder = new ResultObject.Builder<Serializable>();

            // Act & Assert
            assertThrows(NullPointerException.class, () -> builder.extractStateAndDetailsAndErrorCodeFrom(null));
        }

        @Test
        @DisplayName("should provide possibility to reuse previous result")
        void shouldProvidePossibilityToReusePreviousResult() {
            // Arrange
            final var builder = new ResultObject.Builder<Boolean>();
            builder.validDefaultResult(Boolean.FALSE);
            final var anyResultDetail = detailGenerator.next();
            final var anyResult = new ResultObject<>(Generators.nonEmptyStrings().next(), ResultState.WARNING,
                    anyResultDetail);

            // Act
            builder.extractStateAndDetailsAndErrorCodeFrom(anyResult);
            final var buildResult = builder.build();

            // Assert
            assertThat(buildResult.getState(), is(ResultState.WARNING));
            assertThat(buildResult.getResultDetail().get(), is(equalTo(anyResultDetail)));
        }

        @Test
        @DisplayName("should handle multiple result details without state")
        void shouldHandleMultipleResultDetailsWithoutState() {
            // Arrange
            final var builder = new ResultObject.Builder<Boolean>();
            builder.validDefaultResult(Boolean.FALSE);

            // Act
            builder.resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test1")));
            builder.resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")));
            final var buildResult = builder.state(VALID).build();

            // Assert
            assertThat(buildResult.getResultDetail().get(), is(equalTo(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")))));
        }

        @Test
        @DisplayName("should handle multiple result details with state")
        void shouldHandleMultipleResultDetailsWithState() {
            // Arrange
            final var builder = new ResultObject.Builder<Boolean>();
            builder.validDefaultResult(Boolean.FALSE).state(ResultState.ERROR);

            // Act
            builder.resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test1")));
            builder.resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")));
            final var buildResult = builder.build();

            // Assert
            LogAsserts.assertLogMessagePresentContaining(TestLogLevel.ERROR, "Already failed");
            assertThat(buildResult.getResultDetail().get(), is(equalTo(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test2")))));
        }
    }

    @Nested
    @DisplayName("Error code tests")
    class ErrorCodeTests {
        @Test
        @DisplayName("should extract error code")
        void shouldExtractStrategy() {
            assertTrue(SERVICE_NOT_AVAILABLE.getErrorCode().isPresent());
        }

        @Test
        @DisplayName("should check for error code")
        void shouldCheckForStrategy() {
            // Assert
            assertTrue(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.SERVICE_NOT_AVAILABLE));
            assertFalse(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.TEST));
            assertTrue(SERVICE_NOT_AVAILABLE.containsErrorCode(ExampleErrorCodes.TEST,
                    ExampleErrorCodes.SERVICE_NOT_AVAILABLE));
        }
    }

    @Nested
    @DisplayName("Logging tests")
    class LoggingTests {
        @Test
        @DisplayName("should log detail")
        void shouldLogDetail() {
            // Arrange
            final var result = new ResultObject<>("Test", WARNING, new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Test")));

            // Act
            result.logDetail("Prefix", log);

            // Assert
            LogAsserts.assertLogMessagePresentContaining(TestLogLevel.WARN, "Prefix");
        }

        @Test
        @DisplayName("should log throwable")
        void shouldLogThrowable() {
            // Arrange
            final var result = new ResultObject<>("Test", WARNING,
                    new ResultDetail(new LabeledKey("some.key"), new IllegalStateException("b00m")));

            // Act
            result.logDetail("prefix: ", log);

            // Assert
            LogAsserts.assertLogMessagePresentContaining(TestLogLevel.WARN,
                    "prefix: LabeledKey(content=some.key, parameter=[])");
        }
    }

    @Nested
    @DisplayName("Javadoc example tests")
    class JavadocExampleTests {
        
        @Test
        @DisplayName("Should demonstrate service provider implementation pattern")
        void shouldDemonstrateServiceProviderPattern() {
            // Given: A service with various error cases
            var builder = new ResultObject.Builder<String>()
                .validDefaultResult("default");
            
            // When/Then: Invalid parameters
            var badRequest = builder
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Invalid search parameters")))
                .errorCode(ResultErrorCodes.BAD_REQUEST)
                .build();
            assertFalse(badRequest.isValid());
            assertEquals(ResultErrorCodes.BAD_REQUEST, badRequest.getErrorCode().get());
            assertEquals("Invalid search parameters", badRequest.getResultDetail().get().getDetail().getContent());
            
            // When/Then: Not authorized
            var notAuthorized = builder
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Not authorized to view patient data")))
                .errorCode(ResultErrorCodes.NOT_AUTHORIZED)
                .build();
            assertFalse(notAuthorized.isValid());
            assertEquals(ResultErrorCodes.NOT_AUTHORIZED, notAuthorized.getErrorCode().get());
            
            // When/Then: Not found
            var notFound = builder
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Patient not found")))
                .errorCode(ResultErrorCodes.NOT_FOUND)
                .build();
            assertFalse(notFound.isValid());
            assertEquals(ResultErrorCodes.NOT_FOUND, notFound.getErrorCode().get());
            
            // When/Then: Success case
            var success = builder
                .result("Found Patient")
                .state(ResultState.VALID)
                .build();
            assertTrue(success.isValid());
            assertEquals("Found Patient", success.getResult());
        }

        @Test
        @DisplayName("Should demonstrate consumer implementation pattern")
        void shouldDemonstrateConsumerPattern() {
            // Given: Different result scenarios
            var builder = new ResultObject.Builder<String>();
            
            // When/Then: Error case
            var errorResult = builder
                .validDefaultResult("default")
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("Error occurred")))
                .errorCode(ResultErrorCodes.NOT_FOUND)
                .build();
                
            assertFalse(errorResult.isValid());
            assertTrue(errorResult.getResultDetail().isPresent());
            assertTrue(errorResult.getErrorCode().isPresent());
            assertEquals(ResultErrorCodes.NOT_FOUND, errorResult.getErrorCode().get());
            
            // When/Then: Success case
            var validResult = builder
                .result("success data")
                .state(ResultState.VALID)
                .build();
                
            assertTrue(validResult.isValid());
            assertEquals("success data", validResult.getResult());
        }

        @Test
        @DisplayName("Should demonstrate builder usage patterns")
        void shouldDemonstrateBuilderPatterns() {
            // When/Then: Success case
            var success = new ResultObject.Builder<String>()
                .result("user data")
                .state(ResultState.VALID)
                .build();
                
            assertTrue(success.isValid());
            assertEquals("user data", success.getResult());
            
            // When/Then: Error case with default result
            var error = new ResultObject.Builder<String>()
                .validDefaultResult("default user")
                .state(ResultState.ERROR)
                .resultDetail(new ResultDetail(new de.cuioss.uimodel.nameprovider.DisplayName("User creation failed")))
                .errorCode(ResultErrorCodes.BAD_REQUEST)
                .build();
                
            assertFalse(error.isValid());
            // Don't try to get result in error state
            assertEquals(ResultErrorCodes.BAD_REQUEST, error.getErrorCode().get());
            assertEquals("User creation failed", error.getResultDetail().get().getDetail().getContent());
        }
    }
}
