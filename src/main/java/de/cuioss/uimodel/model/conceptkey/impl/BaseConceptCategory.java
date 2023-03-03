package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base implementation of {@link ConceptCategory}, using .getClass().getSimpleName() as
 * name.
 *
 * @author Matthias Walliczek
 */
@ToString
@EqualsAndHashCode(of = "name")
public class BaseConceptCategory implements ConceptCategory {

    private static final long serialVersionUID = 8937041650695445812L;

    /**
     * Constructor using class name.
     */
    public BaseConceptCategory() {
        name = this.getClass().getSimpleName();
    }

    /**
     * Explicit constructor.
     *
     * @param name the name of the category.
     */
    public BaseConceptCategory(final String name) {
        this.name = name;
    }

    @Getter
    private final String name;

    @Override
    public ConceptKeyType createUndefinedConceptKey(final String value) {
        return ConceptKeyTypeImpl.builder().identifier(value)
                .labelResolver(new I18nDisplayNameProvider(value)).category(this)
                .augmentation(AugmentationKeyConstans.UNDEFINED_VALUE, Boolean.TRUE.toString()).build();
    }

}
