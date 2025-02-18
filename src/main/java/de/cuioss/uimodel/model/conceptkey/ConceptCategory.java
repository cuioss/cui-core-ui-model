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
package de.cuioss.uimodel.model.conceptkey;

import java.io.Serializable;

/**
 * Category for an {@link ConceptKeyType} containing a list of mandatory
 * augmentation keys.
 *
 * @author Matthias Walliczek
 */
public interface ConceptCategory extends Serializable {

    /**
     * @return name of the code category. Name is internal use only. could be named
     *         id.
     */
    String getName();

    /**
     * @param value the identifier
     * @return an ConceptKeyType with given value marked with
     *         {@link AugmentationKeyConstants#UNDEFINED_VALUE}
     */
    ConceptKeyType createUndefinedConceptKey(String value);
}
