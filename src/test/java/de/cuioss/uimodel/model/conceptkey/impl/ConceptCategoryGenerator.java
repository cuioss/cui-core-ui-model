package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

@SuppressWarnings("javadoc")
public class ConceptCategoryGenerator implements TypedGenerator<ConceptCategory> {

    private final TypedGenerator<ConceptCategory> generator =
        Generators.fixedValues(ConceptCategory.class, TestCodeCategory.CAT1, TestCodeCategory.CAT2,
                TestCodeCategory.CAT3);

    @Override
    public ConceptCategory next() {
        return generator.next();
    }

    @Override
    public Class<ConceptCategory> getType() {
        return ConceptCategory.class;
    }

    enum TestCodeCategory implements ConceptCategory {
        CAT1,
        CAT2,
        CAT3;

        @Override
        public ConceptKeyType createUndefinedConceptKey(final String value) {
            return ConceptKeyTypeImpl.builder().category(this).identifier(value)
                    .augmentation(AugmentationKeyConstans.UNDEFINED_VALUE, Boolean.TRUE.toString()).build();
        }

        @Override
        public String getName() {
            return name();
        }
    }

}
