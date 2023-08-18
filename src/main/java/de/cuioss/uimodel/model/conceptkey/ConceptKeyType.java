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
package de.cuioss.uimodel.model.conceptkey;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import de.cuioss.uimodel.model.code.CodeType;

/**
 * An extension to {@link CodeType} providing additional information like
 * {@link ConceptCategory} and augmented data. The augmented data is to be kept
 * in a {@link Map}-like structure with {@link Serializable} keys and values.
 * All implementations must be immutable because the approach is about
 * implementing a type-safe-enum pattern, therefore the concrete instances are
 * to be treated like constants. The concept is not about content data but
 * defining data-structures.
 *
 * @author Oliver Wolff
 */
public interface ConceptKeyType extends CodeType, Comparable<ConceptKeyType> {

    /**
     * @return the {@link ConceptCategory} this {@link ConceptKeyType} is related
     *         to. Must never be <code>null</code>
     */
    ConceptCategory getCategory();

    /**
     * @return a set of aliases for this code. Defines whether this code maps to
     *         another identifier of the same category
     */
    Set<String> getAliases();

    /**
     * @param key          identifying the content must not be null nor empty.
     * @param defaultValue optional element that will be returned if there is no
     *                     entry for the given key.
     * @return the object stored under the given key or the defaultValue, if nothing
     *         is stored under the key.
     */
    String get(String key, String defaultValue);

    /**
     * @param key identifying the content must not be null nor empty.
     * @return the object stored under the given key or null if nothing is stored
     *         under the key.
     */
    String get(String key);

    /**
     * @param key identifying the content must not be null nor empty.
     * @return Returns <code>true</code> if the map contains a mapping for the
     *         specified key <code>false</code> if not.
     */
    boolean containsKey(String key);

    /**
     * Returns a {@link Set} view of the mappings contained in the map. The set is
     * never <code>null</code> but may be empty.
     *
     * @return a set view of the mappings contained in this map
     */
    Set<Map.Entry<String, String>> entrySet();
}
