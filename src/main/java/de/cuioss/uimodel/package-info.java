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
 * Provides core UI model components that are framework-agnostic and can be used independently
 * of JSF or any other web framework. These components form the foundation for building
 * robust and maintainable user interface applications.
 *
 * <h2>Core Concepts</h2>
 * <ul>
 *   <li><b>Result Handling</b> - A comprehensive framework for managing service results and errors
 *       in a type-safe manner. See {@link de.cuioss.uimodel.result} package for details.</li>
 *   <li><b>Name Providers</b> - Consistent handling of display names and messages.
 *       See {@link de.cuioss.uimodel.nameprovider} package.</li>
 *   <li><b>Service Integration</b> - Standardized service state management and optional handling.
 *       See {@link de.cuioss.uimodel.service} package.</li>
 *   <li><b>Application Support</b> - Core application components like project stages and
 *       authentication. See {@link de.cuioss.uimodel.application} package.</li>
 * </ul>
 *
 * <h2>Design Principles</h2>
 * <ul>
 *   <li>Framework Independence - Components are designed to work without specific UI frameworks</li>
 *   <li>Type Safety - Strong typing to catch errors at compile time</li>
 *   <li>Immutability - Thread-safe and side-effect free operations</li>
 *   <li>Builder Pattern - Fluent APIs for object construction</li>
 *   <li>Consistent Error Handling - Standardized approach to error management</li>
 * </ul>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.result.ResultObject} - Type-safe wrapper for operation results</li>
 *   <li>{@link de.cuioss.uimodel.nameprovider.DisplayMessageProvider} - Localized message handling</li>
 *   <li>{@link de.cuioss.uimodel.service.OptionalService} - Provides structures and templates for creating robust,
 *   state-aware services that follow the framework's no-exception pattern</li>
 *   <li>{@link de.cuioss.uimodel.application.CuiProjectStage} - Application lifecycle management</li>
 * </ul>
 *
 * <h2>Getting Started</h2>
 * <p>
 * For handling service results and errors, start with the {@link de.cuioss.uimodel.result} package.
 * For display name and message handling, see {@link de.cuioss.uimodel.nameprovider}.
 * For service integration patterns, refer to {@link de.cuioss.uimodel.service}.
 * </p>
 *
 * @author Eugen Fischer
 * @see de.cuioss.uimodel.result.ResultObject
 * @see de.cuioss.uimodel.nameprovider.DisplayMessageProvider
 * @see de.cuioss.uimodel.service.OptionalService
 */
package de.cuioss.uimodel;
