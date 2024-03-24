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
package de.cuioss.uimodel.nameprovider.data;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Entry data implements {@linkplain Entry} interface which can be used for
 * transformation to be added in {@linkplain Map}
 *
 * @author Eugen Fischer
 * @param <K> bounded type for Key must extends {@linkplain Serializable}
 * @param <V> bounded type for Value must extends {@linkplain Serializable}
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class EntryData<K extends Serializable, V extends Serializable> implements Entry<K, V>, Serializable {

    private static final long serialVersionUID = -6403178000941411123L;

    private final K key;

    private V value;

    /**
     * @param key must not be {@code null}
     */
    public EntryData(@NonNull final K key) {
        this.key = key;
    }

    /**
     * @param key   must not be {@code null}
     * @param value
     */
    public EntryData(final K key, final V value) {
        this(key);
        setValue(value);
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }

}
