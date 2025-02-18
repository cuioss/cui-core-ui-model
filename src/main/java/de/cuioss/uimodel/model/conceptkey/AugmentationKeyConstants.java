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

import lombok.experimental.UtilityClass;

/**
 * Basic augmentation keys.
 *
 * @author Matthias Walliczek
 */
@UtilityClass
public final class AugmentationKeyConstants {

    /**
     * Marks one code in a category as default, e.g., to be used to preselect a drop-down box.
     */
    public static final String DEFAULT_VALUE = "default_value";

    /**
     * Marks codes in a category as undefined, e.g. to be used to disable the entry
     * in a drop down box.
     */
    public static final String UNDEFINED_VALUE = "undefined_value";

    /**
     * @param codeType codeType to be checked
     * @return true, if the augmentation key is present and its value is
     *         <code>true</code>.
     */
    public static boolean isUndefinedValue(final ConceptKeyType codeType) {
        return null != codeType && null != codeType.get(AugmentationKeyConstants.UNDEFINED_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstants.UNDEFINED_VALUE));
    }

    /**
     *
     * @param codeType codeType to be checked
     * @return true, if the augmentation key is present and its value is
     *         <code>true</code>.
     */
    public static boolean isDefaultValue(final ConceptKeyType codeType) {
        return null != codeType && null != codeType.get(AugmentationKeyConstants.DEFAULT_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstants.DEFAULT_VALUE));
    }
}
