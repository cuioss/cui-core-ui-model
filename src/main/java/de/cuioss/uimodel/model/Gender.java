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
package de.cuioss.uimodel.model;

import static de.cuioss.tools.string.MoreStrings.nullToEmpty;

import java.util.HashMap;
import java.util.Map;

import de.cuioss.uimodel.nameprovider.LabelKeyProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a gender icon to be consumed by GenderIcon. The factory method is
 * quite fail-safe, see {@link #fromString(String)}
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Gender implements LabelKeyProvider {

    /**
     * Male
     */
    MALE("cui-icon-gender_male", "cui.model.gender.male.title"),

    /**
     * Female
     */
    FEMALE("cui-icon-gender_female", "cui.model.gender.female.title"),

    /**
     * Other
     */
    OTHER("cui-icon-gender_other", "cui.model.gender.other.title"),

    /**
     * Diverse
     */
    DIVERSE("cui-icon-gender_divers", "cui.model.gender.diverse.title"),

    /**
     * Undefined
     */
    UNDEFINED("cui-icon-gender_undefined", "cui.model.gender.undefined.title"),

    /**
     * Unknown
     */
    UNKNOWN("cui-icon-gender_unknown", "cui.model.gender.unknown.title");

    /**
     * css icon class
     */
    @Getter
    private final String cssClass;

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
     * Simple factory method for determining a gender from a given String,
     * algorithm:
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
