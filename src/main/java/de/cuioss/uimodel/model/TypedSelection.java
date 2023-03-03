package de.cuioss.uimodel.model;

/**
 * Interface for a multi selection to be part of a ui model.
 *
 * @param <T> The actual type of the selection
 */
public interface TypedSelection<T> {

    /**
     * @return the selected value
     */
    T getSelectedValue();

}
