package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Represents a field that is computed dynamically by the backend.
 * This is useful for dynamic forms and provide possibility to reset to initialized value.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be {@link Serializable}
 */
public interface TracedDynamicField<T extends Serializable> extends Serializable {

    /**
     * Depending on state indicates if field change is allowed
     *
     * @return true if field is changeable, false otherwise
     */
    boolean isEditable();

    /**
     * @return true if value is available
     */
    boolean isAvailable();

    /**
     * @return null if not available
     */
    T getValue();

    /**
     * Overwrite current value.
     *
     * @param newValue
     * @throws IllegalStateException if value is not allowed to be change in state
     */
    void setValue(T newValue);

    /**
     * If the value has changed since being loaded. Used for optimized saving.
     *
     * @return If changed.
     */
    boolean isChanged();

    /**
     * Resets to the old value.
     *
     * @return the newly set old value
     */
    T resetValue();
}
