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
/**
 * Provides structures and templates for creating robust, state-aware services that follow
 * the framework's no-exception pattern. The package focuses on service availability and
 * state management through {@link de.cuioss.uimodel.service.ServiceState}.
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.service.OptionalService} - Base interface for services
 *       that may be conditionally available</li>
 *   <li>{@link de.cuioss.uimodel.service.ServiceState} - Enumeration defining possible
 *       service states</li>
 * </ul>
 *
 * <h2>Usage Pattern</h2>
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
 * <h2>Integration Points</h2>
 * <ul>
 *   <li>Integrates with {@link de.cuioss.uimodel.result.ResultObject} for error handling</li>
 *   <li>Works with {@link de.cuioss.uimodel.nameprovider.DisplayName} for user messages</li>
 *   <li>Supports runtime state changes and graceful degradation</li>
 * </ul>
 *
 * @author Oliver Wolff
 */
package de.cuioss.uimodel.service;
