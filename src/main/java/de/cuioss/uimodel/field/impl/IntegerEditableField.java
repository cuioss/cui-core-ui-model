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
 * Concrete implementation of {@link BaseDynamicField} for integer values, designed
 * for numeric input scenarios requiring whole numbers within the {@link Integer} range.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles integer values from {@link Integer#MIN_VALUE} to {@link Integer#MAX_VALUE}</li>
 *   <li>Supports null values for optional numeric fields</li>
 *   <li>Automatically uses {@link DynamicFieldType#INTEGER}</li>
 *   <li>Suitable for spinners, number inputs, and numeric form fields</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial value using factory
 * {@code DynamicField<Integer>} field = DynamicFieldType.INTEGER.createDynamicField(42, true);
 *
 * // Create an empty editable field using factory
 * {@code DynamicField<Integer>} emptyField = DynamicFieldType.INTEGER.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class IntegerEditableField extends BaseDynamicField<Integer> {

    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new integer field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public IntegerEditableField(final boolean editable) {
        super(editable, DynamicFieldType.INTEGER);
    }

    /**
     * Constructs a new integer field with the specified value and editability.
     *
     * @param value    The initial integer value, may be null
     * @param editable Whether the field should be editable
     */
    public IntegerEditableField(final Integer value, final boolean editable) {
        super(value, editable, DynamicFieldType.INTEGER);
    }
}
