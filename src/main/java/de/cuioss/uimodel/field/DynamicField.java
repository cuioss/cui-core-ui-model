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
package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Extends {@linkplain TracedDynamicField} by adding type information through
 * {@linkplain DynamicFieldType}. This interface combines value tracking with
 * type-safe field operations, making it suitable for strongly-typed form handling.
 *
 * <p>Usage Examples:
 * <pre>
 * // Create a string field
 * DynamicField&lt;String&gt; stringField = DynamicFieldType.STRING.createDynamicField("value", true);
 * assertEquals(DynamicFieldType.STRING, stringField.getFieldType());
 * assertTrue(stringField.isEditable());
 * 
 * // Create an integer field
 * DynamicField&lt;Integer&gt; intField = DynamicFieldType.INTEGER.createDynamicField(42, true);
 * assertEquals(DynamicFieldType.INTEGER, intField.getFieldType());
 * assertEquals(42, intField.getValue());
 * 
 * // Create a boolean field
 * DynamicField&lt;Boolean&gt; boolField = DynamicFieldType.BOOLEAN.createDynamicField(true, false);
 * assertEquals(DynamicFieldType.BOOLEAN, boolField.getFieldType());
 * assertFalse(boolField.isEditable());
 * </pre>
 *
 * @author Eugen Fischer
 * @author Oliver Wolff
 * @param <T> bounded type must be {@link Serializable}
 */
public interface DynamicField<T extends Serializable> extends TracedDynamicField<T> {

    /**
     * @return the content type of this field.
     */
    DynamicFieldType getFieldType();
}
