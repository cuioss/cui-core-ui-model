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

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

import java.io.Serializable;
import java.util.Set;

import de.cuioss.uimodel.field.impl.BooleanEditableField;
import de.cuioss.uimodel.field.impl.DoubleEditableField;
import de.cuioss.uimodel.field.impl.FloatEditableField;
import de.cuioss.uimodel.field.impl.IntegerEditableField;
import de.cuioss.uimodel.field.impl.LongEditableField;
import de.cuioss.uimodel.field.impl.StringEditableField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Runtime information of {@link DynamicField} regarding the concrete wrapped
 * type
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("unchecked") // owolff: We need to find a better way for implementing the factory
public enum DynamicFieldType {

    /** Represents a {@link DynamicField} for {@link Boolean}. */
    BOOLEAN(Boolean.class, "boolean") {

        @Override
        public DynamicField<Boolean> createDynamicField(final Serializable value, final boolean editable) {
            return new BooleanEditableField((Boolean) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link String}. */
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

    /** Represents a {@link DynamicField} for {@link Integer}. */
    INTEGER(Integer.class, "int") {

        @Override
        public DynamicField<Integer> createDynamicField(final Serializable value, final boolean editable) {
            return new IntegerEditableField((Integer) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Long}. */
    LONG(Long.class, "long") {

        @Override
        public DynamicField<Long> createDynamicField(final Serializable value, final boolean editable) {
            return new LongEditableField((Long) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Long}. */
    FLOAT(Float.class, "float") {

        @Override
        public DynamicField<Float> createDynamicField(final Serializable value, final boolean editable) {
            return new FloatEditableField((Float) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Double}. */
    DOUBLE(Double.class, "double") {

        @Override
        public DynamicField<Double> createDynamicField(final Serializable value, final boolean editable) {
            return new DoubleEditableField((Double) value, editable);
        }
    };

    /** This set defines all elements that are a number. */
    @SuppressWarnings("java:S2386") // owolff: False Positive -> immutable
    public static final Set<DynamicFieldType> NUMBER_SET = immutableSet(INTEGER, LONG, FLOAT, DOUBLE);

    @Getter
    private final Class<? extends Serializable> wrapperType;

    @Getter
    private final String primitiveName;

    /**
     * @return boolean indicating whether the types represents a boolean field
     */
    public boolean isBooleanField() {
        return BOOLEAN.equals(this);
    }

    /**
     * Factory method for creating a corresponding instance of {@link DynamicField}.
     *
     * @param value    may be null
     * @param editable indicates whether the field is editable.
     * @return corresponding instance of {@link DynamicField}.
     */
    public abstract <T extends Serializable> DynamicField<T> createDynamicField(T value, boolean editable);

    /**
     * Factory method for creating an {@link DynamicFieldType} identified by the
     * given type. In case there is no fitting type it will return
     * {@link DynamicFieldType#STRING}
     *
     * @param type must not be null
     * @return {@link DynamicFieldType} identified by the given type. In case there
     *         is no fitting type it will return {@link DynamicFieldType#STRING}
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
