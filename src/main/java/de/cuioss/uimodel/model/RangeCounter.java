package de.cuioss.uimodel.model;

import java.io.Serializable;

/**
 * Some elements display semantics like x elements of y. Therefore this interface is utilized.
 *
 * @author Oliver Wolff
 */
public interface RangeCounter extends Serializable {

    /**
     * @return count of elements
     */
    Integer getCount();

    /**
     * @return total count of elements
     */
    Integer getTotalCount();

    /**
     * @return boolean indicating whether there is a value available for {@link #getCount()}.
     */
    boolean isCountAvailable();

    /**
     * @return boolean indicating whether there is a value available for {@link #getTotalCount()}.
     */
    boolean isTotalCountAvailable();

    /**
     * @return boolean indicating whether there is only one value set.
     */
    boolean isSingleValueOnly();

    /**
     * @return boolean indicating whether there is no set.
     */
    boolean isEmpty();

    /**
     * @return boolean indicating whether all values are set.
     */
    boolean isComplete();
}
