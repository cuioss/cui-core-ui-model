package de.cuioss.uimodel.model.conceptkey;

import java.io.Serializable;

/**
 * Category for an {@link ConceptKeyType} containing a list of mandatory augmentation keys.
 *
 * @author Matthias Walliczek
 */
public interface ConceptCategory extends Serializable {

    /**
     * @return name of the code category. Name is internal use only. could be named id.
     */
    String getName();

    /**
     * @param value the identifier
     * @return an ConceptKeyType with given value marked with
     *         {@link AugmentationKeyConstans#UNDEFINED_VALUE}
     */
    ConceptKeyType createUndefinedConceptKey(String value);
}
