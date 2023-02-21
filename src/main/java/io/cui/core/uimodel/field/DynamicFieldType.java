package io.cui.core.uimodel.field;

import static io.cui.tools.collect.CollectionLiterals.immutableSet;

import java.io.Serializable;
import java.util.Set;

import io.cui.core.uimodel.field.impl.BooleanEditableField;
import io.cui.core.uimodel.field.impl.DoubleEditableField;
import io.cui.core.uimodel.field.impl.FloatEditableField;
import io.cui.core.uimodel.field.impl.IntegerEditableField;
import io.cui.core.uimodel.field.impl.LongEditableField;
import io.cui.core.uimodel.field.impl.StringEditableField;
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
        public DynamicField<Boolean> createDynamicField(final Serializable value,
                final boolean editable) {
            return new BooleanEditableField((Boolean) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link String}. */
    STRING(String.class, "String") {

        @Override
        public DynamicField<String> createDynamicField(final Serializable value,
                final boolean editable) {
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
        public DynamicField<Integer> createDynamicField(final Serializable value,
                final boolean editable) {
            return new IntegerEditableField((Integer) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Long}. */
    LONG(Long.class, "long") {

        @Override
        public DynamicField<Long> createDynamicField(final Serializable value,
                final boolean editable) {
            return new LongEditableField((Long) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Long}. */
    FLOAT(Float.class, "float") {

        @Override
        public DynamicField<Float> createDynamicField(final Serializable value,
                final boolean editable) {
            return new FloatEditableField((Float) value, editable);
        }
    },

    /** Represents a {@link DynamicField} for {@link Double}. */
    DOUBLE(Double.class, "double") {

        @Override
        public DynamicField<Double> createDynamicField(final Serializable value,
                final boolean editable) {
            return new DoubleEditableField((Double) value, editable);
        }
    };

    /** This set defines all elements that are a number. */
    @SuppressWarnings("java:S2386") // owolff: False Positive -> immutable
    public static final Set<DynamicFieldType> NUMBER_SET =
        immutableSet(INTEGER, LONG, FLOAT, DOUBLE);

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
     * Factory method for creating a corresponding instance of
     * {@link DynamicField}.
     *
     * @param value
     *            may be null
     * @param editable
     *            indicates whether the field is editable.
     * @return corresponding instance of {@link DynamicField}.
     */
    public abstract <T extends Serializable> DynamicField<T> createDynamicField(T value, boolean editable);

    /**
     * Factory method for creating an {@link DynamicFieldType} identified by the
     * given type. In case there is no fitting type it will return
     * {@link DynamicFieldType#STRING}
     *
     * @param type
     *            must not be null
     * @return {@link DynamicFieldType} identified by the given type. In case
     *         there is no fitting type it will return
     *         {@link DynamicFieldType#STRING}
     */
    public static final DynamicFieldType getByTypeString(final String type) {
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
