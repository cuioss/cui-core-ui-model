package io.cui.core.uimodel.field;

import java.io.Serializable;

/**
 * @author Matthias Walliczek
 * @param <T>
 */
public interface LabeledDynamicField<T extends Serializable> extends DynamicField<T> {

    /**
     * @return the labelKey
     */
    String getLabelKey();

    /**
     * @return the key for looking up advisory information
     */
    String getAdvisoryKey();

    /**
     * @return the identifier for this field.
     */
    String getIdentifier();
}
