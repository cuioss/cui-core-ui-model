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
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
