package io.cui.core.uimodel.model.conceptkey.impl;

import io.cui.core.uimodel.model.conceptkey.AugmentationKeyConstans;
import io.cui.core.uimodel.model.conceptkey.ConceptCategory;
import io.cui.core.uimodel.model.conceptkey.ConceptKeyType;
import io.cui.test.generator.Generators;
import io.cui.test.generator.TypedGenerator;

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
