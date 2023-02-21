package io.cui.core.uimodel.model.impl;

import io.cui.core.uimodel.model.RangeCounter;
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
        this.count = rangeCounter.getCount();
        this.totalCount = rangeCounter.getTotalCount();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCountAvailable() {
        return null != this.count;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isTotalCountAvailable() {
        return null != this.totalCount;
    }

    @Override
    public boolean isSingleValueOnly() {
        return !isEmpty() && !isComplete();
    }

    @Override
    public boolean isEmpty() {
        return this.count == null && this.totalCount == null;
    }

    @Override
    public boolean isComplete() {
        return this.count != null && this.totalCount != null;
    }

}
