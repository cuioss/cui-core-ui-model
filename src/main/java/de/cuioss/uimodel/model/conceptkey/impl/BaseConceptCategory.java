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

import de.cuioss.uimodel.model.conceptkey.AugmentationKeyConstans;
import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import de.cuioss.uimodel.nameprovider.I18nDisplayNameProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base implementation of {@link ConceptCategory}, using
 * .getClass().getSimpleName() as name.
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
        return ConceptKeyTypeImpl.builder().identifier(value).labelResolver(new I18nDisplayNameProvider(value))
                .category(this).augmentation(AugmentationKeyConstans.UNDEFINED_VALUE, Boolean.TRUE.toString()).build();
    }

}
