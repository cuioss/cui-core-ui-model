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

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;

@SuppressWarnings({ "javadoc", "rawtypes" })
public class ResultObjectGenerator implements TypedGenerator<ResultObject> {

    private static final ResultDetailGenerator DETAIL_GENERATOR = new ResultDetailGenerator();

    @Override
    public ResultObject next() {
        return createAnyValid();
    }

    @Override
    public Class<ResultObject> getType() {
        return ResultObject.class;
    }

    private static ResultObject<?> createAnyValid() {

        return new ResultObject.Builder<>().result(Generators.nonEmptyStrings().next()).state(ResultState.VALID)
                .resultDetail(DETAIL_GENERATOR.next()).build();
    }
}
