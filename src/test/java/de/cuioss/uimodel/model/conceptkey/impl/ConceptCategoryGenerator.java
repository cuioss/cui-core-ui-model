/*
 * Copyright 2023 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstants;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;

public class ConceptCategoryGenerator implements TypedGenerator<ConceptCategory> {

    private final TypedGenerator<ConceptCategory> generator = Generators.fixedValues(ConceptCategory.class,
            TestCodeCategory.CAT1, TestCodeCategory.CAT2, TestCodeCategory.CAT3);

    @Override
    public ConceptCategory next() {
        return generator.next();
    }

    @Override
    public Class<ConceptCategory> getType() {
        return ConceptCategory.class;
    }

    enum TestCodeCategory implements ConceptCategory {
        CAT1, CAT2, CAT3;

        @Override
        public ConceptKeyType createUndefinedConceptKey(final String value) {
            return ConceptKeyTypeImpl.builder().category(this).identifier(value)
                    .augmentation(AugmentationKeyConstants.UNDEFINED_VALUE, Boolean.TRUE.toString()).build();
        }

        @Override
        public String getName() {
            return name();
        }
    }

}
