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
package de.cuioss.uimodel.result;

import de.cuioss.test.generator.Generators;
import de.cuioss.test.generator.TypedGenerator;
import de.cuioss.uimodel.nameprovider.DisplayName;
import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import de.cuioss.uimodel.nameprovider.LabeledKey;

public class ResultDetailGenerator implements TypedGenerator<ResultDetail> {

    @Override
    public ResultDetail next() {
        return ResultDetail.builder().detail(anyDisplayNameProvider()).cause(Generators.throwables().next()).build();
    }

    @Override
    public Class<ResultDetail> getType() {
        return ResultDetail.class;
    }

    private static IDisplayNameProvider<?> anyDisplayNameProvider() {
        return Generators.booleans().next() ? new DisplayName(Generators.letterStrings(5, 50).next())
                : new LabeledKey(Generators.letterStrings(5, 50).next());
    }
}
