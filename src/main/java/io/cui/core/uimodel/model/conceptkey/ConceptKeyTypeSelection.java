package io.cui.core.uimodel.model.conceptkey;

import io.cui.core.uimodel.model.TypedSelection;

/**
 * Interface for a {@link ConceptKeyType} multi selection to be part of a ui model.
 */
public interface ConceptKeyTypeSelection extends TypedSelection<ConceptKeyType> {

    /**
     * @return the selected {@link ConceptKeyType}
     */
    @Override
    ConceptKeyType getSelectedValue();

}
