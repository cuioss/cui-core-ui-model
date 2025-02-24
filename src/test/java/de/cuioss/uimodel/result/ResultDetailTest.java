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

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@PropertyReflectionConfig(skip = true)
@DisplayName("ResultDetail Tests")
class ResultDetailTest extends ValueObjectTest<ResultDetail> {

    private final ResultDetailGenerator generator = new ResultDetailGenerator();

    @Override
    protected ResultDetail anyValueObject() {
        return generator.next();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        @Test
        @DisplayName("should build with IDisplayNameProvider")
        void shouldBuildWithDisplayNameProvider() {
            // Arrange
            final IDisplayNameProvider<String> dnProvider = new de.cuioss.uimodel.nameprovider.DisplayName("test");

            // Act & Assert
            assertDoesNotThrow(() -> new ResultDetail(dnProvider),
                    "Constructor call with IDisplayNameProvider failed");
        }

        @Test
        @DisplayName("should build with IDisplayNameProvider and Throwable")
        void shouldBuildWithDisplayNameProviderAndThrowable() {
            // Arrange
            final IDisplayNameProvider<String> dnProvider = new de.cuioss.uimodel.nameprovider.DisplayName("test");
            final Throwable throwable = new RuntimeException("test");

            // Act & Assert
            assertDoesNotThrow(() -> new ResultDetail(dnProvider, throwable),
                    "Constructor call with IDisplayNameProvider and Throwable failed");
        }

        @Test
        @DisplayName("should build with builder")
        void shouldBuildWithBuilder() {
            // Arrange
            final IDisplayNameProvider<String> dnProvider = new de.cuioss.uimodel.nameprovider.DisplayName("test");
            final Throwable throwable = new RuntimeException("test");

            // Act
            ResultDetail detail = ResultDetail.builder()
                    .detail(dnProvider)
                    .cause(throwable)
                    .build();

            // Assert
            assertNotNull(detail);
            assertNotNull(detail.getDetail());
            assertNotNull(detail.getCause());
        }

        @Test
        @DisplayName("should build with message")
        void shouldBuildWithMessage() {
            // Arrange
            final IDisplayNameProvider<String> dnProvider = new de.cuioss.uimodel.nameprovider.DisplayName("test message");

            // Act & Assert
            assertDoesNotThrow(() -> new ResultDetail(dnProvider),
                    "Constructor call with message failed");
        }
    }

    @Nested
    @DisplayName("Javadoc example tests")
    class JavadocExampleTests {

        @Test
        @DisplayName("Should demonstrate basic error detail")
        void shouldDemonstrateBasicErrorDetail() {
            // When: Creating basic error detail
            var detail = new ResultDetail(
                    new de.cuioss.uimodel.nameprovider.DisplayName("Operation failed")
            );

            // Then: Detail is properly set
            assertNotNull(detail.getDetail());
            assertEquals("Operation failed", detail.getDetail().getContent());
            assertFalse(detail.getCause().isPresent());
        }

        @Test
        @DisplayName("Should demonstrate error with cause")
        void shouldDemonstrateErrorWithCause() {
            // Given: A service exception
            var serviceException = new RuntimeException("Database connection failed");

            // When: Creating error detail with cause
            var detail = new ResultDetail(
                    new de.cuioss.uimodel.nameprovider.DisplayName("Database connection failed"),
                    serviceException
            );

            // Then: Both message and cause are set
            assertEquals("Database connection failed", detail.getDetail().getContent());
            assertTrue(detail.getCause().isPresent());
            assertEquals(serviceException, detail.getCause().get());
        }

        @Test
        @DisplayName("Should demonstrate builder pattern")
        void shouldDemonstrateBuilderPattern() {
            // Given: A validation exception
            var validationException = new IllegalArgumentException("Invalid input");

            // When: Using builder to create detail
            var detail = ResultDetail.builder()
                    .detail(new de.cuioss.uimodel.nameprovider.DisplayName("Validation failed"))
                    .cause(validationException)
                    .build();

            // Then: Detail is properly constructed
            assertEquals("Validation failed", detail.getDetail().getContent());
            assertTrue(detail.getCause().isPresent());
            assertEquals(validationException, detail.getCause().get());
        }

        @Test
        @DisplayName("Should demonstrate internationalized messages")
        void shouldDemonstrateInternationalizedMessages() {
            // When: Creating detail with message provider
            var messageFormat = new de.cuioss.uimodel.nameprovider.DisplayMessageFormat(
                    "error.notfound",
                    "User", "123"
            );
            var detail = new ResultDetail(
                    new de.cuioss.uimodel.nameprovider.DisplayMessageProvider(messageFormat)
            );

            // Then: Message format is properly set
            assertNotNull(detail.getDetail());
            assertEquals("error.notfound",
                    ((de.cuioss.uimodel.nameprovider.DisplayMessageFormat) detail.getDetail().getContent()).getMsgKey());
        }
    }
}
