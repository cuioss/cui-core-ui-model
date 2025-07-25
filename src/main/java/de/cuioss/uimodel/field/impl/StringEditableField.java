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
package de.cuioss.uimodel.field.impl;

import de.cuioss.uimodel.field.DynamicFieldType;

import java.io.Serial;

/**
 * Concrete implementation of {@link BaseDynamicField} for string values, designed
 * for text input scenarios. This field is suitable for single-line text inputs,
 * text areas, and rich text editors.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles text input of any length</li>
 *   <li>Supports null values for optional fields</li>
 *   <li>Automatically uses {@link DynamicFieldType#STRING}</li>
 *   <li>Maintains all change tracking capabilities</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial text using factory
 * {@code DynamicField<String>} field = DynamicFieldType.STRING.createDynamicField("Initial text", true);
 *
 * // Create an empty editable field using factory
 * {@code DynamicField<String>} emptyField = DynamicFieldType.STRING.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class StringEditableField extends BaseDynamicField<String> {

    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new string field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public StringEditableField(final boolean editable) {
        super(editable, DynamicFieldType.STRING);
    }

    /**
     * Constructs a new string field with the specified value and editability.
     *
     * @param value    The initial string value, may be null
     * @param editable Whether the field should be editable
     */
    public StringEditableField(final String value, final boolean editable) {
        super(value, editable, DynamicFieldType.STRING);
    }
}
