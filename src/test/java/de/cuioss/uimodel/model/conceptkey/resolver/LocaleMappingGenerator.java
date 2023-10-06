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
package de.cuioss.uimodel.model.conceptkey.resolver;

import static de.cuioss.test.generator.Generators.integers;
import static de.cuioss.test.generator.Generators.locales;
import static de.cuioss.test.generator.Generators.nonEmptyStrings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.cuioss.test.generator.TypedGenerator;

@SuppressWarnings({ "rawtypes" })
public class LocaleMappingGenerator implements TypedGenerator<Map> {

    private final TypedGenerator<String> labels = nonEmptyStrings();

    private final TypedGenerator<Integer> counter = integers(0, 12);

    @Override
    public Map<Locale, String> next() {
        final Map<Locale, String> map = new HashMap<>();
        final int size = counter.next();
        for (var i = 0; i < size; i++) {
            map.put(locales().next(), labels.next());
        }
        return map;
    }

    @Override
    public Class<Map> getType() {
        return Map.class;
    }

}
