package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * A {@linkplain TracedDynamicField} that is read-only per default, but can be unlocked upon demand.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be {@link Serializable}
 */
public interface UnlockableTracedDynamicField<T extends Serializable> extends TracedDynamicField<T> {

    /**
     * execute action unlock edit mode
     */
    void unlockEditMode();

    /**
     * Retrieve information if edit mode was enforced.
     *
     * @return {@code true} if edit mode was overwritten, {@code false} otherwise
     */
    boolean isEditModeEnforced();

    /**
     * execute action to reset edit mode
     */
    void resetEditMode();
}
