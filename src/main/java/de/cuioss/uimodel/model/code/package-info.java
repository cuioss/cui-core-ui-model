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
 * Provides a comprehensive framework for handling code-based data structures and their
 * transformations across different system components. This package is designed to
 * standardize the way codes (such as status codes, type codes, or reference codes)
 * are represented, compared, and transmitted throughout the application.
 *
 * <p>Key Components:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeType} - Core interface defining the contract for code types</li>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeTypeImpl} - Standard implementation of the CodeType interface</li>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeTypeComparator} - Utility for comparing code types</li>
 *   <li>{@link de.cuioss.uimodel.model.code.GenderCodeType} - Specialized implementation for gender codes</li>
 * </ul>
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe code handling</li>
 *   <li>Consistent comparison and sorting</li>
 *   <li>Immutable implementations</li>
 *   <li>Serialization support</li>
 *   <li>Localization capabilities</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Creating a new code type
 * CodeType statusCode = new CodeTypeImpl("ACTIVE", "Active Status");
 *
 * // Using the comparator
 * List&lt;CodeType&gt; codes = new ArrayList&lt;&gt;();
 * codes.sort(new CodeTypeComparator());
 * </pre>
 *
 * @author Sven Haag
 * @since 1.0
 */
package de.cuioss.uimodel.model.code;
