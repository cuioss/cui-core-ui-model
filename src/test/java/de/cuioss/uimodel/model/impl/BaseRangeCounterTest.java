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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.contracts.VerifyCopyConstructor;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.model.RangeCounter;

@PropertyReflectionConfig(of = {"count", "totalCount"})
@VerifyConstructor(of = {"count", "totalCount"})
@VerifyCopyConstructor(argumentType = RangeCounter.class)
class BaseRangeCounterTest extends ValueObjectTest<BaseRangeCounter> {

    @Test
    void baseRangeCounterRangeCounter() {

        final var count = 10;
        final var totalCount = 100;
        var checkCount = 0;

        final var baserangeCounter = new BaseRangeCounter(count, totalCount);
        final var baserangeCounter2 = new BaseRangeCounter(baserangeCounter);
        checkCount = baserangeCounter2.getCount();

        assertEquals(count, checkCount);
        assertTrue(baserangeCounter2.isCountAvailable());
        assertTrue(baserangeCounter.isTotalCountAvailable());
        assertFalse(baserangeCounter.isSingleValueOnly());
        assertTrue(baserangeCounter.isComplete());
        assertFalse(baserangeCounter.isEmpty());

    }

    @Test
    void baseRangeCounterRangeCounter2() {

        final var baserangeCounter = new BaseRangeCounter(null, null);

        assertFalse(baserangeCounter.isCountAvailable());
        assertFalse(baserangeCounter.isTotalCountAvailable());
        assertFalse(baserangeCounter.isSingleValueOnly());
        assertTrue(baserangeCounter.isEmpty());
        assertFalse(baserangeCounter.isComplete());

    }

    @Test
    void baseRangeCounterRangeCounter3() {

        final var count = 0;
        final var totalCount = 0;

        final var baserangeCounter = new BaseRangeCounter(count, totalCount);

        assertTrue(baserangeCounter.isCountAvailable());
        assertFalse(baserangeCounter.isEmpty());
        assertFalse(baserangeCounter.isSingleValueOnly());
        assertTrue(baserangeCounter.isComplete());

    }

    @Test
    void baseRangeCounterRangeCounter4() {

        final var count = 1;

        final var baserangeCounter = new BaseRangeCounter(count, null);

        assertTrue(baserangeCounter.isSingleValueOnly());

    }

    @Test
    void baseRangeCounterRangeCounter5() {
        final var totalCount = 0;
        final var baserangeCounter = new BaseRangeCounter(null, totalCount);

        assertTrue(baserangeCounter.isSingleValueOnly());

    }
}
