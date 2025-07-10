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
 * Concrete implementation of {@link BaseDynamicField} for boolean values, typically
 * used for yes/no or true/false selections in forms. This field is particularly
 * suited for checkbox, toggle switch, or radio button inputs.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles boolean values (true/false)</li>
 *   <li>Supports null values for tri-state scenarios</li>
 *   <li>Automatically uses {@link DynamicFieldType#BOOLEAN}</li>
 *   <li>Maintains all change tracking capabilities</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial value using factory
 * {@code DynamicField<Boolean>} field = DynamicFieldType.BOOLEAN.createDynamicField(true, true);
 *
 * // Create an editable field without initial value using factory
 * {@code DynamicField<Boolean>} emptyField = DynamicFieldType.BOOLEAN.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class BooleanEditableField extends BaseDynamicField<Boolean> {

    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new boolean field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public BooleanEditableField(final boolean editable) {
        super(editable, DynamicFieldType.BOOLEAN);
    }

    /**
     * Constructs a new boolean field with the specified value and editability.
     *
     * @param value    The initial boolean value, may be null
     * @param editable Whether the field should be editable
     */
    public BooleanEditableField(final Boolean value, final boolean editable) {
        super(value, editable, DynamicFieldType.BOOLEAN);
    }
}
