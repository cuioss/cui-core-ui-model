package io.cui.core.uimodel.model.conceptkey;

import lombok.experimental.UtilityClass;

/**
 * Basic augmentation keys.
 *
 * @author Matthias Walliczek
 */
@UtilityClass
public final class AugmentationKeyConstans {

    /**
     * Marks one code in a category as default, e.g. to be used to preselect a drop down box.
     */
    public static final String DEFAULT_VALUE = "default_value";

    /**
     * Marks codes in a category as undefined, e.g. to be used to disable the entry in a drop down
     * box.
     */
    public static final String UNDEFINED_VALUE = "undefined_value";

    /**
     * @param codeType codeType to be checked
     * @return true, if the augmentation key is present and its value is <code>true</code>.
     */
    public static boolean isUndefinedValue(final ConceptKeyType codeType) {
        return null != codeType
                && null != codeType.get(AugmentationKeyConstans.UNDEFINED_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstans.UNDEFINED_VALUE));
    }

    /**
     *
     * @param codeType codeType to be checked
     * @return true, if the augmentation key is present and its value is <code>true</code>.
     */
    public static boolean isDefaultValue(final ConceptKeyType codeType) {
        return null != codeType
                && null != codeType.get(AugmentationKeyConstans.DEFAULT_VALUE)
                && Boolean.parseBoolean(codeType.get(AugmentationKeyConstans.DEFAULT_VALUE));
    }
}
