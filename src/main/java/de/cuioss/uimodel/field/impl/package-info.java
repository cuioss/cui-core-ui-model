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
 * Provides concrete implementations for the dynamic field framework defined in
 * {@link de.cuioss.uimodel.field}. This package contains base classes and
 * type-specific implementations that form the core functionality of the field system.
 *
 * <h2>Base Implementations</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseDynamicField} - Core field functionality</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseTracedDynamicField} - Change tracking support</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseLabeledDynamicField} - UI labeling support</li>
 * </ul>
 *
 * <h2>Type-Specific Fields</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.BooleanEditableField} - Boolean values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.StringEditableField} - String values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.IntegerEditableField} - Integer values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.LongEditableField} - Long values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.FloatEditableField} - Float values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.DoubleEditableField} - Double values</li>
 * </ul>
 *
 * <h2>Special Implementations</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.UnlockableTracedDynamicFieldImpl} - Protected fields</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.field.impl;
