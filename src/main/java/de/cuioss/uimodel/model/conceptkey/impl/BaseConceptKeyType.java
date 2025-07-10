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
package de.cuioss.uimodel.model.conceptkey.impl;

import de.cuioss.uimodel.model.conceptkey.ConceptCategory;
import de.cuioss.uimodel.model.conceptkey.ConceptKeyType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * Base abstract implementation of {@link ConceptKeyType}, implementing aliases,
 * augmentationMap and category handling.
 *
 * @author Matthias Walliczek
 */
@EqualsAndHashCode(of = {"category"})
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseConceptKeyType implements ConceptKeyType {

    @Serial
    private static final long serialVersionUID = 3314726756126201321L;

    @Getter
    private final Set<String> aliases;

    private final Map<String, String> augmentationMap;

    @Getter
    private final ConceptCategory category;

    protected BaseConceptKeyType(final ConceptCategory category) {
        this(new TreeSet<>(), new HashMap<>(), category);
    }

    @Override
    public String get(final String key, final String defaultValue) {
        if (!augmentationMap.containsKey(key)) {
            return defaultValue;
        }
        return augmentationMap.get(key);
    }

    @Override
    public String get(final String key) {
        return get(key, null);
    }

    protected void set(final String key, final String value) {
        augmentationMap.put(key, value);
    }

    @Override
    public boolean containsKey(final String key) {
        return augmentationMap.containsKey(key);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return augmentationMap.entrySet();
    }

}
