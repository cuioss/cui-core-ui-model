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
 * Defines the possible states of a service, providing a consistent way to handle
 * service availability without relying on exceptions. This enum is used in conjunction
 * with {@link OptionalService} to implement robust service state management.
 *
 * <h2>State Transitions</h2>
 * A service typically transitions through these states based on:
 * <ul>
 *   <li>Configuration status</li>
 *   <li>Runtime availability</li>
 *   <li>User authorization</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>
 * public class DataService {
 *     private final ServiceState serviceState;
 *     
 *     public ResultObject&lt;String&gt; getData(String id) {
 *         switch (serviceState) {
 *             case ACTIVE:
 *                 return new ResultObject.Builder&lt;String&gt;()
 *                     .result("Data for " + id)
 *                     .state(ResultState.VALID)
 *                     .build();
 *                     
 *             case TEMPORARILY_UNAVAILABLE:
 *                 return new ResultObject.Builder&lt;String&gt;()
 *                     .state(ResultState.ERROR)
 *                     .resultDetail(new ResultDetail(
 *                         new DisplayName("Service temporarily unavailable")))
 *                     .build();
 *                     
 *             case NOT_CONFIGURED:
 *                 return new ResultObject.Builder&lt;String&gt;()
 *                     .state(ResultState.ERROR)
 *                     .resultDetail(new ResultDetail(
 *                         new DisplayName("Service not properly configured")))
 *                     .build();
 *                     
 *             case NOT_AVAILABLE_FOR_USER:
 *                 return new ResultObject.Builder&lt;String&gt;()
 *                     .state(ResultState.ERROR)
 *                     .resultDetail(new ResultDetail(
 *                         new DisplayName("Not authorized to access this service")))
 *                     .build();
 *                     
 *             default:
 *                 throw new IllegalStateException("Unknown service state");
 *         }
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 */
public enum ServiceState {

    /**
     * The service is active and can be accessed. This is the normal operating state
     * where all conditions are met:
     * <ul>
     *   <li>Service is properly configured</li>
     *   <li>All required resources are available</li>
     *   <li>Current user has necessary permissions</li>
     * </ul>
     */
    ACTIVE,

    /**
     * Indicates the service is properly configured but currently not available.
     * This is typically a transient state that may occur due to:
     * <ul>
     *   <li>Network connectivity issues</li>
     *   <li>Backend system maintenance</li>
     *   <li>Resource constraints (e.g., connection pool exhausted)</li>
     * </ul>
     */
    TEMPORARILY_UNAVAILABLE,

    /**
     * Indicates that the service is not configured at all or the configuration is
     * incomplete or invalid. This state typically occurs when:
     * <ul>
     *   <li>Required configuration properties are missing</li>
     *   <li>Configuration values are invalid</li>
     *   <li>Required resources cannot be initialized</li>
     * </ul>
     */
    NOT_CONFIGURED,

    /**
     * Indicates that while the service is {@link #ACTIVE}, it cannot be used by the
     * currently logged-in user. This state typically occurs when:
     * <ul>
     *   <li>User lacks required permissions</li>
     *   <li>User's account has restrictions</li>
     *   <li>Service access is limited by time or other conditions</li>
     * </ul>
     */
    NOT_AVAILABLE_FOR_USER
}
