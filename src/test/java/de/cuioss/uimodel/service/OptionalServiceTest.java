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
package de.cuioss.uimodel.service;

import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.result.ResultDetail;
import de.cuioss.uimodel.result.ResultObject;
import de.cuioss.uimodel.result.ResultState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@org.junit.jupiter.api.DisplayName("OptionalService Tests")
class OptionalServiceTest {

    // Test implementation of OptionalService using the documentation example
    private static class FileStorageService implements OptionalService {
        private final boolean configValid;
        private final boolean storageAccessible;
        private final boolean userHasAccess;

        FileStorageService(boolean configValid, boolean storageAccessible, boolean userHasAccess) {
            this.configValid = configValid;
            this.storageAccessible = storageAccessible;
            this.userHasAccess = userHasAccess;
        }

        @Override
        public ServiceState getServiceState() {
            if (!configValid) {
                return ServiceState.NOT_CONFIGURED;
            }
            if (!storageAccessible) {
                return ServiceState.TEMPORARILY_UNAVAILABLE;
            }
            if (!userHasAccess) {
                return ServiceState.NOT_AVAILABLE_FOR_USER;
            }
            return ServiceState.ACTIVE;
        }

        public ResultObject<String> storeFile(String content) {
            if (!isServiceAvailable()) {
                return new ResultObject.Builder<String>()
                        .state(ResultState.ERROR)
                        .result("")  // Empty string as result for error case
                        .resultDetail(new ResultDetail(
                                new DisplayName("Storage service not available: " + getServiceState())))
                        .build();
            }

            return new ResultObject.Builder<String>()
                    .result("Stored: " + content)
                    .state(ResultState.VALID)
                    .build();
        }
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle active service state")
    void shouldHandleActiveState() {
        // Arrange
        var service = new FileStorageService(true, true, true);

        // Act & Assert
        assertEquals(ServiceState.ACTIVE, service.getServiceState());
        assertTrue(service.isServiceAvailable());

        var result = service.storeFile("test");
        assertTrue(result.isValid());
        assertEquals("Stored: test", result.getResult());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle not configured state")
    void shouldHandleNotConfiguredState() {
        // Arrange
        var service = new FileStorageService(false, true, true);

        // Act & Assert
        assertEquals(ServiceState.NOT_CONFIGURED, service.getServiceState());
        assertFalse(service.isServiceAvailable());

        var result = service.storeFile("test");
        assertFalse(result.isValid());
        assertEquals("Storage service not available: NOT_CONFIGURED",
                result.getResultDetail().get().getDetail().getContent());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle temporarily unavailable state")
    void shouldHandleTemporarilyUnavailableState() {
        // Arrange
        var service = new FileStorageService(true, false, true);

        // Act & Assert
        assertEquals(ServiceState.TEMPORARILY_UNAVAILABLE, service.getServiceState());
        assertFalse(service.isServiceAvailable());

        var result = service.storeFile("test");
        assertFalse(result.isValid());
        assertEquals("Storage service not available: TEMPORARILY_UNAVAILABLE",
                result.getResultDetail().get().getDetail().getContent());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("should handle not available for user state")
    void shouldHandleNotAvailableForUserState() {
        // Arrange
        var service = new FileStorageService(true, true, false);

        // Act & Assert
        assertEquals(ServiceState.NOT_AVAILABLE_FOR_USER, service.getServiceState());
        assertFalse(service.isServiceAvailable());

        var result = service.storeFile("test");
        assertFalse(result.isValid());
        assertEquals("Storage service not available: NOT_AVAILABLE_FOR_USER",
                result.getResultDetail().get().getDetail().getContent());
    }
}
