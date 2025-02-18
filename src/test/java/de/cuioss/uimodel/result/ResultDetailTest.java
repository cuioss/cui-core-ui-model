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
package de.cuioss.uimodel.result;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


import org.junit.jupiter.api.Test;

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.property.PropertyReflectionConfig;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;

@PropertyReflectionConfig(skip = true)
class ResultDetailTest extends ValueObjectTest<ResultDetail> {

    private final ResultDetailGenerator generator = new ResultDetailGenerator();

    @Override
    protected ResultDetail anyValueObject() {
        return generator.next();
    }

    @Test
    void shouldBuildWithConstructors() {
        final IDisplayNameProvider<String> dnProvider = new DisplayName("test");
        final Throwable ex = new Exception("b00m");

        assertDoesNotThrow(() -> new ResultDetail(dnProvider), "Constructor call with IDisplayNameProvider failed");

        assertDoesNotThrow(() -> new ResultDetail(dnProvider, ex), "Constructor call with Throwable failed");

    }
}
