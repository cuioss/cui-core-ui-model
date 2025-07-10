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
package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.DynamicFieldType;

import java.io.Serial;

/**
 * Concrete implementation of {@link BaseDynamicField} for floating-point values,
 * designed for numeric input scenarios requiring decimal precision with
 * single-precision floating-point format.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles float values from {@link Float#MIN_VALUE} to {@link Float#MAX_VALUE}</li>
 *   <li>Supports null values for optional numeric fields</li>
 *   <li>Automatically uses {@link DynamicFieldType#FLOAT}</li>
 *   <li>Suitable for measurements, percentages, and scientific calculations</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial value using factory
 * {@code DynamicField<Float>} field = DynamicFieldType.FLOAT.createDynamicField(3.14f, true);
 *
 * // Create an empty editable field using factory
 * {@code DynamicField<Float>} emptyField = DynamicFieldType.FLOAT.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class FloatEditableField extends BaseDynamicField<Float> {

    /** */
    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new float field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public FloatEditableField(final boolean editable) {
        super(editable, DynamicFieldType.FLOAT);
    }

    /**
     * Constructs a new float field with the specified value and editability.
     *
     * @param value    The initial float value, may be null
     * @param editable Whether the field should be editable
     */
    public FloatEditableField(final Float value, final boolean editable) {
        super(value, editable, DynamicFieldType.FLOAT);
    }

}
