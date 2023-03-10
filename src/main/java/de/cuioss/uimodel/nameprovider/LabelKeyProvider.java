package de.cuioss.uimodel.nameprovider;

/**
 * This interfaces defines objects that provide LabelKeys.
 *
 * @author Oliver Wolff
 */
@FunctionalInterface
public interface LabelKeyProvider {

    /**
     * @return a label key corresponding to the given object.
     */
    String getLabelKey();
}
