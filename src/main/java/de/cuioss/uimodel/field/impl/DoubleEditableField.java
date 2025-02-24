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
 * Concrete implementation of {@link BaseDynamicField} for double-precision values,
 * designed for numeric input scenarios requiring high decimal precision with
 * double-precision floating-point format.
 *
 * <p>Features:
 * <ul>
 *   <li>Handles double values from {@link Double#MIN_VALUE} to {@link Double#MAX_VALUE}</li>
 *   <li>Supports null values for optional numeric fields</li>
 *   <li>Automatically uses {@link DynamicFieldType#DOUBLE}</li>
 *   <li>Suitable for high-precision calculations and financial data</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Create an editable field with initial value using factory
 * {@code DynamicField<Double>} field = DynamicFieldType.DOUBLE.createDynamicField(3.14159265359, true);
 *
 * // Create an empty editable field using factory
 * {@code DynamicField<Double>} emptyField = DynamicFieldType.DOUBLE.createDynamicField(null, true);
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public class DoubleEditableField extends BaseDynamicField<Double> {

    /** */
    @Serial
    private static final long serialVersionUID = 1576605569211546203L;

    /**
     * Constructs a new double field with the specified editability.
     * The initial value will be null.
     *
     * @param editable Whether the field should be editable
     */
    public DoubleEditableField(final boolean editable) {
        super(editable, DynamicFieldType.DOUBLE);
    }

    /**
     * Constructs a new double field with the specified value and editability.
     *
     * @param value    The initial double value, may be null
     * @param editable Whether the field should be editable
     */
    public DoubleEditableField(final Double value, final boolean editable) {
        super(value, editable, DynamicFieldType.DOUBLE);
    }

}
