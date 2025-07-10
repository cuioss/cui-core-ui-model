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
package de.cuioss.uimodel.nameprovider.data;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A type-safe implementation of {@link Entry} that supports transformation and storage
 * in {@link Map} structures. This class provides a serializable key-value pair with
 * strong type guarantees and null-safety for keys.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Type-safe key and value handling</li>
 *   <li>Serialization support</li>
 *   <li>Null-safe key handling</li>
 *   <li>Mutable value support</li>
 *   <li>Proper equals/hashCode implementation</li>
 * </ul>
 *
 * <p>Type Parameters:
 * <ul>
 *   <li>{@code K} - The key type, must extend {@link Serializable}</li>
 *   <li>{@code V} - The value type, must extend {@link Serializable}</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Basic usage with String key and Integer value
 * EntryData&lt;String, Integer&gt; entry = new EntryData&lt;&gt;("count", 42);
 * System.out.println(entry.getKey() + ": " + entry.getValue());
 *
 * // Using in a Map
 * Map&lt;String, Integer&gt; map = new HashMap&lt;&gt;();
 * map.put(entry.getKey(), entry.getValue());
 *
 * // Updating value
 * entry.setValue(100);
 *
 * // Creating with initial key only
 * EntryData&lt;String, User&gt; userEntry = new EntryData&lt;&gt;("user");
 * userEntry.setValue(new User("John"));
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Keys are immutable once set</li>
 *   <li>Keys cannot be null (enforced by {@link NonNull})</li>
 *   <li>Values can be null</li>
 *   <li>The class is serializable for distributed environments</li>
 *   <li>ToString provides human-readable output</li>
 *   <li>Equals/hashCode implementation ignores getters for performance</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @param <K> The key type, must extend {@link Serializable}
 * @param <V> The value type, must extend {@link Serializable}
 * @since 1.0
 * @see Entry
 * @see Serializable
 * @see Map
 */
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class EntryData<K extends Serializable, V extends Serializable> implements Entry<K, V>, Serializable {

    @Serial
    private static final long serialVersionUID = -6403178000941411123L;

    /**
     * The immutable key of this entry. Cannot be null.
     */
    private final K key;

    /**
     * The mutable value of this entry. Can be null.
     */
    private V value;

    /**
     * Creates a new entry with the specified key and no initial value.
     * The value can be set later using {@link #setValue(Object)}.
     *
     * @param key The key for this entry, must not be {@code null}
     * @throws NullPointerException if the key is null
     */
    public EntryData(@NonNull final K key) {
        this.key = key;
    }

    /**
     * Creates a new entry with the specified key and value.
     * This is a convenience constructor that combines key setting
     * and value initialization.
     *
     * @param key The key for this entry, must not be {@code null}
     * @param value The initial value for this entry, may be null
     * @throws NullPointerException if the key is null
     */
    public EntryData(final K key, final V value) {
        this(key);
        setValue(value);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the key of this entry. The key is immutable and
     * guaranteed to be non-null.
     *
     * @return the non-null key of this entry
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the current value of this entry. The value may be null
     * if it hasn't been set or was explicitly set to null.
     *
     * @return the current value of this entry, may be null
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sets a new value for this entry and returns the new value.
     * The value can be null.
     *
     * @param value the new value to set, may be null
     * @return the new value that was set
     */
    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }
}
