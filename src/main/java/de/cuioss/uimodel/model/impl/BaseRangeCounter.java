/**
 * Copyright © 2025 CUI-OpenSource-Software (info@cuioss.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.cuioss.uimodel.model.impl;

import de.cuioss.uimodel.model.RangeCounter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * A straightforward implementation of the {@link RangeCounter} interface that
 * provides basic range counting functionality. This class is particularly useful
 * for pagination, progress tracking, and partial data loading scenarios.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Immutable design</li>
 *   <li>Null-safe value handling</li>
 *   <li>Comprehensive state tracking</li>
 *   <li>Copy constructor support</li>
 *   <li>Proper equals/hashCode implementation</li>
 * </ul>
 *
 * <p>State Management:
 * <ul>
 *   <li>Empty: Both count and totalCount are null</li>
 *   <li>Single Value: Either count or totalCount is available</li>
 *   <li>Complete: Both count and totalCount are available</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Create a complete range counter
 * BaseRangeCounter complete = new BaseRangeCounter(10, 100);
 * System.out.println("Showing " + complete.getCount() + " of " + complete.getTotalCount());
 *
 * // Create a partial range counter
 * BaseRangeCounter partial = new BaseRangeCounter(5, null);
 * if (partial.isSingleValueOnly()) {
 *     System.out.println("Showing " + partial.getCount() + " items");
 * }
 *
 * // Create an empty range counter
 * BaseRangeCounter empty = new BaseRangeCounter(null, null);
 * if (empty.isEmpty()) {
 *     System.out.println("No count information available");
 * }
 *
 * // Copy an existing counter
 * BaseRangeCounter copy = new BaseRangeCounter(complete);
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>All state checks are null-safe</li>
 *   <li>The class is serializable for distributed environments</li>
 *   <li>ToString provides human-readable output</li>
 *   <li>Proper equals/hashCode for collection usage</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 * @see RangeCounter
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class BaseRangeCounter implements RangeCounter {

    @Serial
    private static final long serialVersionUID = -1535931081715308509L;

    /**
     * The current count value. May be null if no count is available.
     * This represents the number of items currently being displayed or processed.
     */
    @Getter
    private final Integer count;

    /**
     * The total count value. May be null if no total count is available.
     * This represents the total number of items that could potentially be displayed
     * or processed.
     */
    @Getter
    private final Integer totalCount;

    /**
     * Copy constructor that creates a new instance with the same state as the
     * provided range counter. This is useful when you need to create a snapshot
     * of the counter state or work with a copy in a different context.
     *
     * @param rangeCounter the counter to copy from, must not be null
     * @throws NullPointerException if rangeCounter is null
     */
    public BaseRangeCounter(final RangeCounter rangeCounter) {
        count = rangeCounter.getCount();
        totalCount = rangeCounter.getTotalCount();
    }

    /**
     * {@inheritDoc}
     * <p>
     * A count is considered available if the {@link #count} value is not null.
     * This indicates that we know how many items are currently being displayed
     * or processed.
     */
    @Override
    public boolean isCountAvailable() {
        return null != count;
    }

    /**
     * {@inheritDoc}
     * <p>
     * A total count is considered available if the {@link #totalCount} value
     * is not null. This indicates that we know the total number of items that
     * could potentially be displayed or processed.
     */
    @Override
    public boolean isTotalCountAvailable() {
        return null != totalCount;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The counter is considered to have a single value if it has either count
     * or totalCount, but not both. This typically indicates a partial state
     * where we only know one of the two values.
     */
    @Override
    public boolean isSingleValueOnly() {
        return !isEmpty() && !isComplete();
    }

    /**
     * {@inheritDoc}
     * <p>
     * The counter is considered empty if both count and totalCount are null.
     * This typically indicates an uninitialized or reset state.
     */
    @Override
    public boolean isEmpty() {
        return count == null && totalCount == null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The counter is considered complete if both count and totalCount are
     * available (not null). This indicates we have full information about
     * both the current and total counts.
     */
    @Override
    public boolean isComplete() {
        return count != null && totalCount != null;
    }
}
