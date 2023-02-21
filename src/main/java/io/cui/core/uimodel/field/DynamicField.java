package io.cui.core.uimodel.field;

import java.io.Serializable;

/**
 * Extends {@linkplain TracedDynamicField} and include {@linkplain DynamicFieldType} information
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
