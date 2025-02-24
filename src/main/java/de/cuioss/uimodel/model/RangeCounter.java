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

import java.io.Serializable;

/**
 * Defines a contract for tracking and displaying count relationships, typically used
 * for showing progress, pagination, or subset relationships (e.g., "showing X of Y items").
 * This interface provides methods to access and validate both the current count and
 * total count values.
 *
 * <p>Features:
 * <ul>
 *   <li>Current count and total count tracking</li>
 *   <li>Null-safe value handling</li>
 *   <li>State validation methods</li>
 *   <li>Serializable for state persistence</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Pagination displays (Page 5 of 10)</li>
 *   <li>Search results (Showing 20 of 100 matches)</li>
 *   <li>Progress indicators (3 of 5 steps completed)</li>
 *   <li>Subset relationships (Selected 2 of 5 items)</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class PaginationCounter implements RangeCounter {
 *     private Integer currentPage;
 *     private Integer totalPages;
 *
 *     &#64;Override
 *     public Integer getCount() {
 *         return currentPage;
 *     }
 *
 *     &#64;Override
 *     public Integer getTotalCount() {
 *         return totalPages;
 *     }
 *
 *     &#64;Override
 *     public boolean isComplete() {
 *         return currentPage != null
 *             &amp;&amp; totalPages != null;
 *     }
 *
 *     public String getDisplayText() {
 *         if (isComplete()) {
 *             return String.format("Page %d of %d", currentPage, totalPages);
 *         }
 *         return "No pages available";
 *     }
 * }
 * </pre>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public interface RangeCounter extends Serializable {

    /**
     * Returns the current count value, which typically represents a subset
     * or current position within a total range.
     *
     * @return the current count value, may be null if not set
     */
    Integer getCount();

    /**
     * Returns the total count value, which represents the complete range
     * or maximum value for the counter.
     *
     * @return the total count value, may be null if not set
     */
    Integer getTotalCount();

    /**
     * Checks if a current count value is available.
     *
     * @return {@code true} if {@link #getCount()} returns a non-null value,
     *         {@code false} otherwise
     */
    boolean isCountAvailable();

    /**
     * Checks if a total count value is available.
     *
     * @return {@code true} if {@link #getTotalCount()} returns a non-null value,
     *         {@code false} otherwise
     */
    boolean isTotalCountAvailable();

    /**
     * Checks if only one of the values (count or total count) is set.
     * This is useful for identifying partial or incomplete range states.
     *
     * @return {@code true} if exactly one of count or total count is set,
     *         {@code false} otherwise
     */
    boolean isSingleValueOnly();

    /**
     * Checks if no values are set (both count and total count are null).
     *
     * @return {@code true} if neither count nor total count is set,
     *         {@code false} if at least one value is available
     */
    boolean isEmpty();

    /**
     * Checks if both count and total count values are set (non-null).
     * This indicates a fully defined range.
     *
     * @return {@code true} if both count and total count are set,
     *         {@code false} if either value is missing
     */
    boolean isComplete();
}
