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
 * Some elements display semantics like x elements of y. Therefore this
 * interface is utilized.
 *
 * @author Oliver Wolff
 */
public interface RangeCounter extends Serializable {

    /**
     * @return count of elements
     */
    Integer getCount();

    /**
     * @return total count of elements
     */
    Integer getTotalCount();

    /**
     * @return boolean indicating whether there is a value available for
     *         {@link #getCount()}.
     */
    boolean isCountAvailable();

    /**
     * @return boolean indicating whether there is a value available for
     *         {@link #getTotalCount()}.
     */
    boolean isTotalCountAvailable();

    /**
     * @return boolean indicating whether there is only one value set.
     */
    boolean isSingleValueOnly();

    /**
     * @return boolean indicating whether there is no set.
     */
    boolean isEmpty();

    /**
     * @return boolean indicating whether all values are set.
     */
    boolean isComplete();
}
