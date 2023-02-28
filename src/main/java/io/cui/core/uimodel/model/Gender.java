package io.cui.core.uimodel.model;

import static io.cui.tools.string.MoreStrings.nullToEmpty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a gender icon to be consumed by GenderIcon. The factory method is quite fail-safe, see
 * {@link #fromString(String)}
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender implements Serializable {

    /**
     * Male
     */
    MALE("cui.model.gender.male.title"),

    /**
     * Female
     */
    FEMALE("cui.model.gender.female.title"),

    /**
     * Other
     */
    OTHER("cui.model.gender.other.title"),

    /**
     * Diverse
     */
    DIVERSE("cui.model.gender.diverse.title"),

    /**
     * Undefined
     */
    UNDEFINED("cui.model.gender.undefined.title"),

    /**
     * Unknown
     */
    UNKNOWN("cui.model.gender.unknown.title");

    /**
     * msg key for title
     */
    @Getter
    private final String labelKey;

    private static final Map<String, Gender> MAPPING = initMapping();

    private static Map<String, Gender> initMapping() {
        final Map<String, Gender> result = new HashMap<>(6);
        result.put("m", MALE);
        result.put("male", MALE);
        result.put("f", FEMALE);
        result.put("female", FEMALE);
        result.put("o", OTHER);
        result.put("other", OTHER);
        result.put("d", DIVERSE);
        result.put("diverse", DIVERSE);
        result.put("x", UNDEFINED);
        result.put("undefined", UNDEFINED);
        return result;
    }

    /**
     * Simple factory method for determining a gender from a given String, algorithm:
     * <ul>
     * <li>if String is null or empty: {@link Gender#UNKNOWN}</li>
     * <li>if String is "m" or "male": {@link Gender#MALE}</li>
     * <li>if String is "f" or "female": {@link Gender#FEMALE}</li>
     * <li>if String is "o" or "other": {@link Gender#OTHER}</li>
     * <li>if String is "d" or "diverse": {@link Gender#DIVERSE}</li>
     * <li>if String is "x" or "undefined": {@link Gender#UNDEFINED}</li>
     * <li>For all other strings it returns {@link Gender#UNKNOWN}</li>
     * </ul>
     *
     * @param genderString may be {@code null} or empty
     * @return the computed {@linkplain Gender} object.
     */
    public static final Gender fromString(final String genderString) {
        final var key = nullToEmpty(genderString).toLowerCase();
        return MAPPING.getOrDefault(key, UNKNOWN);
    }
}
