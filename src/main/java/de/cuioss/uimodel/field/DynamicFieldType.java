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

import de.cuioss.uimodel.field.impl.BooleanEditableField;
import de.cuioss.uimodel.field.impl.DoubleEditableField;
import de.cuioss.uimodel.field.impl.FloatEditableField;
import de.cuioss.uimodel.field.impl.IntegerEditableField;
import de.cuioss.uimodel.field.impl.LongEditableField;
import de.cuioss.uimodel.field.impl.StringEditableField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

/**
 * Defines the supported field types for {@link DynamicField} implementations with factory
 * capabilities for creating type-specific instances. This enum provides a type-safe way to
 * handle different field types and their corresponding implementations.
 *
 * <p>Supported field types:
 * <ul>
 *   <li>{@link #BOOLEAN} - For boolean values</li>
 *   <li>{@link #STRING} - For text values</li>
 *   <li>{@link #INTEGER} - For integer values</li>
 *   <li>{@link #LONG} - For long integer values</li>
 *   <li>{@link #FLOAT} - For floating-point values</li>
 *   <li>{@link #DOUBLE} - For double-precision values</li>
 * </ul>
 *
 * <p>Each field type provides:
 * <ul>
 *   <li>Type-safe factory methods for creating corresponding {@link DynamicField} instances</li>
 *   <li>Type information for UI rendering and validation</li>
 *   <li>Proper value conversion and handling</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see DynamicField
 * @see TracedDynamicField
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unchecked") // owolff: We need to find a better way for implementing the factory
public enum DynamicFieldType {

    /** 
     * Represents a {@link DynamicField} for {@link Boolean} values.
     * Suitable for checkboxes, toggles, and yes/no selections.
     */
    BOOLEAN(Boolean.class, "boolean") {
        @Override
        public DynamicField<Boolean> createDynamicField(final Serializable value, final boolean editable) {
            return new BooleanEditableField((Boolean) value, editable);
        }
    },

    /** 
     * Represents a {@link DynamicField} for {@link String} values.
     * Suitable for text input, text areas, and rich text editors.
     */
    STRING(String.class, "String") {
        @Override
        public DynamicField<String> createDynamicField(final Serializable value, final boolean editable) {
            String stringValue = null;
            if (value != null) {
                stringValue = String.valueOf(value);
            }
            return new StringEditableField(stringValue, editable);
        }
    },

    /** 
     * Represents a {@link DynamicField} for {@link Integer} values.
     * Suitable for whole number inputs within the integer range.
     */
    INTEGER(Integer.class, "int") {
        @Override
        public DynamicField<Integer> createDynamicField(final Serializable value, final boolean editable) {
            return new IntegerEditableField((Integer) value, editable);
        }
    },

    /** 
     * Represents a {@link DynamicField} for {@link Long} values.
     * Suitable for large whole numbers exceeding integer range.
     */
    LONG(Long.class, "long") {
        @Override
        public DynamicField<Long> createDynamicField(final Serializable value, final boolean editable) {
            return new LongEditableField((Long) value, editable);
        }
    },

    /** 
     * Represents a {@link DynamicField} for {@link Float} values.
     * Suitable for decimal numbers with single precision.
     */
    FLOAT(Float.class, "float") {
        @Override
        public DynamicField<Float> createDynamicField(final Serializable value, final boolean editable) {
            return new FloatEditableField((Float) value, editable);
        }
    },

    /** 
     * Represents a {@link DynamicField} for {@link Double} values.
     * Suitable for decimal numbers requiring high precision.
     */
    DOUBLE(Double.class, "double") {
        @Override
        public DynamicField<Double> createDynamicField(final Serializable value, final boolean editable) {
            return new DoubleEditableField((Double) value, editable);
        }
    };

    /** This set defines all elements that are numeric types. */
    public static final Set<DynamicFieldType> NUMBER_TYPES = immutableSet(INTEGER, LONG, FLOAT, DOUBLE);

    @Getter
    private final Class<? extends Serializable> wrapperType;

    @Getter
    private final String primitiveName;

    /**
     * Determines if this field type represents a boolean value.
     * This is useful for UI components that need to render appropriate
     * boolean controls like checkboxes or toggles.
     *
     * @return {@code true} if this type represents a boolean field,
     *         {@code false} otherwise
     */
    public boolean isBooleanField() {
        return BOOLEAN.equals(this);
    }

    /**
     * Creates a new {@link DynamicField} instance for this field type.
     * The created field will be initialized with the given value and
     * editability state.
     *
     * <p>Each field type implementation ensures proper type conversion
     * and validation of the input value.
     *
     * @param value The initial value for the field. May be null.
     * @param editable Whether the field should be editable.
     * @return A new {@link DynamicField} instance configured with the
     *         specified parameters
     * @throws IllegalArgumentException if the value type doesn't match
     *         the field type
     */
    public abstract <T extends Serializable> DynamicField<T> createDynamicField(T value, boolean editable);

    /**
     * Resolves a {@link DynamicFieldType} from its string representation.
     * This is particularly useful when working with configuration or
     * serialized data.
     *
     * <p>The method performs a case-insensitive search for a matching type.
     * If no matching type is found, returns {@link #STRING} as a safe default.
     *
     * @param type The string representation of the type. Must not be null.
     * @return The corresponding {@link DynamicFieldType}, or {@link #STRING}
     *         if no match is found
     * @throws NullPointerException if type is null
     */
    public static DynamicFieldType getByTypeString(final String type) {
        var fieldType = DynamicFieldType.STRING;

        for (final DynamicFieldType dynamicFieldType : DynamicFieldType.values()) {
            if (dynamicFieldType.getWrapperType().getName().equals(type)
                    || dynamicFieldType.primitiveName.equals(type)) {
                fieldType = dynamicFieldType;
                break;
            }
        }

        return fieldType;
    }
}
