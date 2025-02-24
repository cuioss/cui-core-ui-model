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
package de.cuioss.uimodel.service;

import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@org.junit.jupiter.api.DisplayName("ServiceState Tests")
class ServiceStateTest {

    // Test implementation using the documentation example
    private static class DataService {
        private final ServiceState serviceState;

        DataService(ServiceState serviceState) {
            this.serviceState = serviceState;
        }

        ResultObject<String> getData(String id) {
            return switch (serviceState) {
                case ACTIVE -> new ResultObject.Builder<String>()
                        .result("Data for " + id)
                        .state(ResultState.VALID)
                        .build();
                case TEMPORARILY_UNAVAILABLE -> new ResultObject.Builder<String>()
                        .state(ResultState.ERROR)
                        .result("")
                        .resultDetail(new ResultDetail(
                                new DisplayName("Service temporarily unavailable")))
                        .build();
                case NOT_CONFIGURED -> new ResultObject.Builder<String>()
                        .state(ResultState.ERROR)
                        .result("")
                        .resultDetail(new ResultDetail(
                                new DisplayName("Service not properly configured")))
                        .build();
                case NOT_AVAILABLE_FOR_USER -> new ResultObject.Builder<String>()
                        .state(ResultState.ERROR)
                        .result("")
                        .resultDetail(new ResultDetail(
                                new DisplayName("Not authorized to access this service")))
                        .build();
                default -> throw new IllegalStateException("Unknown service state");
            };
        }
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle active state")
    void shouldHandleActiveState() {
        // Arrange
        var service = new DataService(ServiceState.ACTIVE);

        // Act
        var result = service.getData("123");

        // Assert
        assertTrue(result.isValid());
        assertEquals("Data for 123", result.getResult());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle temporarily unavailable state")
    void shouldHandleTemporarilyUnavailableState() {
        // Arrange
        var service = new DataService(ServiceState.TEMPORARILY_UNAVAILABLE);

        // Act
        var result = service.getData("123");

        // Assert
        assertFalse(result.isValid());
        assertEquals("Service temporarily unavailable",
                result.getResultDetail().get().getDetail().getContent());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle not configured state")
    void shouldHandleNotConfiguredState() {
        // Arrange
        var service = new DataService(ServiceState.NOT_CONFIGURED);

        // Act
        var result = service.getData("123");

        // Assert
        assertFalse(result.isValid());
        assertEquals("Service not properly configured",
                result.getResultDetail().get().getDetail().getContent());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle not available for user state")
    void shouldHandleNotAvailableForUserState() {
        // Arrange
        var service = new DataService(ServiceState.NOT_AVAILABLE_FOR_USER);

        // Act
        var result = service.getData("123");

        // Assert
        assertFalse(result.isValid());
        assertEquals("Not authorized to access this service",
                result.getResultDetail().get().getDetail().getContent());
    }
}
