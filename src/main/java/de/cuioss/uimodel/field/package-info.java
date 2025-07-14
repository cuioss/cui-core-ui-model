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
 * Provides a comprehensive framework for implementing dynamic form fields with type-safety,
 * change tracking, and UI integration capabilities.
 *
 * <h2>Core Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.TracedDynamicField} - Base interface for value tracking</li>
 *   <li>{@link de.cuioss.uimodel.field.DynamicField} - Type-safe field with metadata</li>
 *   <li>{@link de.cuioss.uimodel.field.DynamicFieldType} - Type system for field values</li>
 *   <li>{@link de.cuioss.uimodel.field.LabeledDynamicField} - Fields with UI labels</li>
 *   <li>{@link de.cuioss.uimodel.field.UnlockableTracedDynamicField} - Protected fields</li>
 * </ul>
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Type-safe value handling</li>
 *   <li>Change tracking for form state</li>
 *   <li>UI integration support</li>
 *   <li>Field protection mechanisms</li>
 *   <li>Extensible field types</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.field;
