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
 * A value object that wraps an integer value with boundary validation capabilities.
 * This class is particularly useful for handling numeric inputs that must fall within
 * specific ranges, such as dates, months, or bounded numeric values.
 *
 * <p>Features:
 * <ul>
 *   <li>Null-safe value handling</li>
 *   <li>Built-in boundary validation</li>
 *   <li>Specialized factories for common use cases (year, month, DAX)</li>
 *   <li>Immutable implementation</li>
 * </ul>
 *
 * <p>The class provides predefined bounds for common scenarios:
 * <ul>
 *   <li>Years: 0 to 5000</li>
 *   <li>Months: 0 to 13</li>
 *   <li>DAX: 0 to 32</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create a year instance
 * ConditionalInteger year = ConditionalInteger.createYearInstance(2025);
 * if (year.isValidAndInBound()) {
 *     // Process valid year
 * }
 *
 * // Create a month instance
 * ConditionalInteger month = ConditionalInteger.createMonthInstance(12);
 * Integer monthValue = month.getContent();
 *
 * // Create a custom bounded instance
 * ConditionalInteger custom = new ConditionalInteger(42, 0, 100);
 * </pre>
 *
 * @author Eugen Fischer
 * @since 1.0
 */
@ToString(of = "content", doNotUseGetters = true)
@EqualsAndHashCode(of = "content", doNotUseGetters = true)
public class ConditionalInteger implements Serializable {

    /** Maximum bound for DAX values */
    private static final int DAX_MAX_BOUND = 32;

    /** Maximum bound for month values (13 to include possibility of 13th month) */
    private static final int MONTH_MAX_BOUND = 13;

    /** Maximum bound for year values */
    private static final int YEAR_MAX_BOUND = 5000;

    /** Minimum bound for all values */
    private static final int MIN_BOUND = 0;

    /**
     * Empty instance of {@link ConditionalInteger} with null content.
     * Use this constant instead of creating new empty instances.
     */
    public static final ConditionalInteger EMPTY_INSTANCE = new ConditionalInteger(null, MIN_BOUND, MIN_BOUND);

    @Serial
    private static final long serialVersionUID = 5495286309563797727L;

    /**
     * The wrapped integer value. May be null to represent no value.
     */
    @Getter
    private final Integer content;

    /**
     * Indicates whether the content is non-null and falls within the specified bounds.
     * Returns {@code true} if the value is valid and within bounds, {@code false} otherwise.
     */
    @Getter
    private final boolean validAndInBound;

    /**
     * Creates a new ConditionalInteger with the specified value and bounds.
     * The value is considered valid if it is non-null and falls within the
     * specified bounds (exclusive).
     *
     * @param content  The integer value to wrap, may be null
     * @param minBound The minimum bound (exclusive)
     * @param maxBound The maximum bound (exclusive)
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
     * Creates a ConditionalInteger for year values. Years are considered valid
     * if they fall between 0 and 5000 (exclusive).
     *
     * @param year The year value to wrap, may be null
     * @return A new ConditionalInteger instance for the year, or {@link #EMPTY_INSTANCE}
     *         if the input is null
     */
    public static ConditionalInteger createYearInstance(final Integer year) {
        if (null == year) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(year, MIN_BOUND, YEAR_MAX_BOUND);
    }

    /**
     * Creates a ConditionalInteger for month values. Months are considered valid
     * if they fall between 0 and 13 (exclusive). The upper bound of 13 allows
     * for systems that use a 13th month.
     *
     * @param month The month value to wrap, may be null
     * @return A new ConditionalInteger instance for the month, or {@link #EMPTY_INSTANCE}
     *         if the input is null
     */
    public static ConditionalInteger createMonthInstance(final Integer month) {
        if (null == month) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(month, MIN_BOUND, MONTH_MAX_BOUND);
    }

    /**
     * Creates a ConditionalInteger for day values. Days are considered valid
     * if they fall between 0 and 32 (exclusive).
     *
     * @param day The day value to wrap, may be null
     * @return A new ConditionalInteger instance for the day, or {@link #EMPTY_INSTANCE}
     *         if the input is null
     */
    public static ConditionalInteger createDayInstance(final Integer day) {
        if (null == day) {
            return EMPTY_INSTANCE;
        }
        return new ConditionalInteger(day, MIN_BOUND, DAX_MAX_BOUND);
    }
}
