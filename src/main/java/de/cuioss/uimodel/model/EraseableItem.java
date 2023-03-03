package de.cuioss.uimodel.model;

import java.io.Serializable;

/**
 * Wrapper provide delete flag information
 *
 * @author Eugen Fischer
 */
public interface EraseableItem extends Serializable {

    /**
     * @return current flag value
     */
    boolean isMarkedAsDeleted();

    /**
     * Toggle delete flag value
     */
    void toggleDeleteFlag();

}
