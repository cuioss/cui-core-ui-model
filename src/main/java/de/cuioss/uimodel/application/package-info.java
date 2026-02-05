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
 * Provides core application-level components for managing application state,
 * authentication, and environment configuration. This package contains classes
 * that are fundamental to application setup and operation but are not specific
 * to any particular UI component or business logic.
 *
 * <h2>Key Components</h2>
 *
 * <h3>Project Stage Management</h3>
 * <p>The {@link de.cuioss.uimodel.application.CuiProjectStage} interface provides
 * a framework-agnostic way to manage application stages (development, test,
 * production). This abstraction allows for consistent stage-based configuration
 * across different environments without tying to specific implementations like
 * JSF or DeltaSpike.
 *
 * <h3>Authentication</h3>
 * <p>The {@link de.cuioss.uimodel.application.LoginCredentials} class offers
 * a structured approach to handling form-based authentication data. It includes:
 * <ul>
 *   <li>Secure credential management</li>
 *   <li>Multi-tenant support via user stores</li>
 *   <li>"Remember me" functionality</li>
 *   <li>Built-in validation</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>Environment Configuration</h3>
 * <pre>
 * &#64;Inject
 * private CuiProjectStage projectStage;
 *
 * public void configureApplication() {
 *     if (projectStage.isDevelopment()) {
 *         enableDebugFeatures();
 *     } else if (projectStage.isProduction()) {
 *         enableProductionOptimizations();
 *     }
 * }
 * </pre>
 *
 * <h3>Authentication Handling</h3>
 * <pre>
 * // Create and validate credentials
 * LoginCredentials credentials = LoginCredentials.builder()
 *     .username(formUsername)
 *     .password(formPassword)
 *     .userStore("LDAP")
 *     .build();
 *
 * if (credentials.isComplete()) {
 *     authenticationService.authenticate(credentials);
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.application;
