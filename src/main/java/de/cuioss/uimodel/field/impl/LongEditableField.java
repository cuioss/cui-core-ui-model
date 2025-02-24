/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
 * Concrete implementation of {@link BaseDynamicField} for long integer values,
 * designed for numeric input scenarios requiring whole numbers beyond the
 * {@link Integer} range.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles long values from {@link Long#MIN_VALUE} to {@link Long#MAX_VALUE}</li>
 *   <li>Supports null values for optional numeric fields</li>
 *   <li>Automatically uses {@link DynamicFieldType#LONG}</li>
 *   <li>Suitable for large numeric values like timestamps or IDs</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial value using factory
 * {@code DynamicField<Long>} field = DynamicFieldType.LONG.createDynamicField(System.currentTimeMillis(), true);
 *
 * // Create an empty editable field using factory
 * {@code DynamicField<Long>} emptyField = DynamicFieldType.LONG.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class LongEditableField extends BaseDynamicField<Long> {

    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new long field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public LongEditableField(final boolean editable) {
        super(editable, DynamicFieldType.LONG);
    }

    /**
     * Constructs a new long field with the specified value and editability.
     *
     * @param value    The initial long value, may be null
     * @param editable Whether the field should be editable
     */
    public LongEditableField(final Long value, final boolean editable) {
        super(value, editable, DynamicFieldType.LONG);
    }
}
