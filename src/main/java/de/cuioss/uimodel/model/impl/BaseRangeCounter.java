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
package de.cuioss.uimodel.model.impl;

import java.io.Serial;

import de.cuioss.uimodel.model.RangeCounter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Simple Implementation for {@link RangeCounter}
 *
 * @author Oliver Wolff
 */
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class BaseRangeCounter implements RangeCounter {

    @Serial
    private static final long serialVersionUID = -1535931081715308509L;

    @Getter
    private final Integer count;

    @Getter
    private final Integer totalCount;

    /**
     * Copy Constructor
     *
     * @param rangeCounter must not be null
     */
    public BaseRangeCounter(final RangeCounter rangeCounter) {
        count = rangeCounter.getCount();
        totalCount = rangeCounter.getTotalCount();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCountAvailable() {
        return null != count;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isTotalCountAvailable() {
        return null != totalCount;
    }

    @Override
    public boolean isSingleValueOnly() {
        return !isEmpty() && !isComplete();
    }

    @Override
    public boolean isEmpty() {
        return count == null && totalCount == null;
    }

    @Override
    public boolean isComplete() {
        return count != null && totalCount != null;
    }

}
