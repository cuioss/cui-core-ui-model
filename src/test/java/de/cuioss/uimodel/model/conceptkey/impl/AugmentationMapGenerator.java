/*
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
package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.test.generator.TypedGenerator;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes"})
public class AugmentationMapGenerator implements TypedGenerator<Map> {

    public static final String KEY1 = "key1";

    public static final String KEY2 = "key2";

    public static final String INVALID_KEY = "invalidkey";

    public static final String VALUE1 = Integer.toString(1);

    public static final String VALUE2 = "value2";

    @Override
    public Map<String, String> next() {
        final Map<String, String> augmentationMap = new HashMap<>();
        augmentationMap.put(KEY1, VALUE1);
        augmentationMap.put(KEY2, VALUE2);
        return augmentationMap;
    }

    @Override
    public Class<Map> getType() {
        return Map.class;
    }

}
