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
package de.cuioss.uimodel.service;

/**
 * Base interface for services that may be conditionally available based on configuration,
 * runtime state, or user context. This interface provides a consistent way to handle
 * service availability without relying on exceptions.
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>State-based availability checking</li>
 *   <li>User context awareness</li>
 *   <li>Integration with {@link de.cuioss.uimodel.result.ResultObject}</li>
 *   <li>Default implementation for common cases</li>
 * </ul>
 *
 * <h2>Implementation Example</h2>
 * <pre>
 * public class FileStorageService implements OptionalService {
 *     private final boolean configValid;
 *     private final boolean storageAccessible;
 *     private final boolean userHasAccess;
 *     
 *     &#64;Override
 *     public ServiceState getServiceState() {
 *         if (!configValid) {
 *             return ServiceState.NOT_CONFIGURED;
 *         }
 *         if (!storageAccessible) {
 *             return ServiceState.TEMPORARILY_UNAVAILABLE;
 *         }
 *         if (!userHasAccess) {
 *             return ServiceState.NOT_AVAILABLE_FOR_USER;
 *         }
 *         return ServiceState.ACTIVE;
 *     }
 *     
 *     public ResultObject&lt;String&gt; storeFile(String content) {
 *         if (!isServiceAvailable()) {
 *             return new ResultObject.Builder&lt;String&gt;()
 *                 .state(ResultState.ERROR)
 *                 .resultDetail(new ResultDetail(
 *                     new DisplayName("Storage service not available: " + getServiceState())))
 *                 .build();
 *         }
 *         
 *         return new ResultObject.Builder&lt;String&gt;()
 *             .result("Stored: " + content)
 *             .state(ResultState.VALID)
 *             .build();
 *     }
 * }
 * </pre>
 *
 * <h2>Usage Notes</h2>
 * <ul>
 *   <li>Always check service availability before performing operations</li>
 *   <li>Use {@link #isServiceAvailable()} for simple checks</li>
 *   <li>Use {@link #getServiceState()} when specific state handling is needed</li>
 *   <li>Consider caching the service state if it's expensive to compute</li>
 * </ul>
 *
 * @author Oliver Wolff
 *
 */
public interface OptionalService {

    /**
     * Returns the current state of the service. The default implementation returns
     * {@link ServiceState#ACTIVE}, assuming the service is always available.
     * Override this method to implement proper state checking based on your
     * service's requirements.
     *
     * @return The current {@link ServiceState} of the service. Never returns null.
     */
    default ServiceState getServiceState() {
        return ServiceState.ACTIVE;
    }

    /**
     * Shortcut for checking whether the service is available for the current call
     * context. This is equivalent to checking if {@link #getServiceState()} equals
     * {@link ServiceState#ACTIVE}.
     *
     * @return {@code true} if the service is up, running, and accessible for the
     *         current logged-in user; {@code false} otherwise
     */
    default boolean isServiceAvailable() {
        return ServiceState.ACTIVE.equals(getServiceState());
    }
}
