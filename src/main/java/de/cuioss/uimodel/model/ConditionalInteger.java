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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * Provide additional information for integer value
 *
 * @author Eugen Fischer
 */
@ToString(of = "content", doNotUseGetters = true)
@EqualsAndHashCode(of = "content", doNotUseGetters = true)
public class ConditionalInteger implements Serializable {

    private static final int DAX_MAX_BOUND = 32;

    private static final int MONTH_MAX_BOUND = 13;

    private static final int YEAR_MAX_BOUND = 5000;

    private static final int MIN_BOUND = 0;

    /**
     * Empty instance of {@linkplain ConditionalInteger}
     */
    public static final ConditionalInteger EMPTY_INSTANCE = new ConditionalInteger(null, MIN_BOUND, MIN_BOUND);

    @Serial
    private static final long serialVersionUID = 5495286309563797727L;

    @Getter
    private final Integer content;

    /**
     * return {@code true} if content is not {@code null} and in bound
     */
    @Getter
    private final boolean validAndInBound;

    /**
     * ConditionalInteger constructor
     *
     * @param content  {@linkplain Integer} could be {@code null}
     * @param minBound min bound value
     * @param maxBound max bound value
     */
    public ConditionalInteger(final Integer content, final int minBound, final int maxBound) {
        if (null == content) {
            this.content = null;
            validAndInBound = false;
        } else {
            this.content = content;
            final int valueData = content;
            validAndInBound = valueData > minBound && valueData < maxBound;
        }
    }

    /**
     * Create instance with minBound = 0 and maxBound = 5000
     *
     * @param year {@linkplain Integer} could be {@code null}
     * @return {@linkplain ConditionalInteger}
     */
    public static ConditionalInteger createYearInstance(final Integer year) {
        if (null == year) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(year, MIN_BOUND, YEAR_MAX_BOUND);
    }

    /**
     * Create instance with minBound = 0 and maxBound = 13
     *
     * @param month {@linkplain Integer} could be {@code null}
     * @return {@linkplain ConditionalInteger}
     */
    public static ConditionalInteger createMonthInstance(final Integer month) {
        if (null == month) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(month, MIN_BOUND, MONTH_MAX_BOUND);
    }

    /**
     * Create instance with minBound = 0 and maxBound = 32
     *
     * @param day {@linkplain Integer} could be {@code null}
     * @return {@linkplain ConditionalInteger}
     */
    public static ConditionalInteger createDayInstance(final Integer day) {
        if (null == day) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(day, MIN_BOUND, DAX_MAX_BOUND);
    }
}
