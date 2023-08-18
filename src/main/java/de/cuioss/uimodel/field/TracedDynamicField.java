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
package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Represents a field that is computed dynamically by the backend. This is
 * useful for dynamic forms and provide possibility to reset to initialized
 * value.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be {@link Serializable}
 */
public interface TracedDynamicField<T extends Serializable> extends Serializable {

    /**
     * Depending on state indicates if field change is allowed
     *
     * @return true if field is changeable, false otherwise
     */
    boolean isEditable();

    /**
     * @return true if value is available
     */
    boolean isAvailable();

    /**
     * @return null if not available
     */
    T getValue();

    /**
     * Overwrite current value.
     *
     * @param newValue
     * @throws IllegalStateException if value is not allowed to be change in state
     */
    void setValue(T newValue);

    /**
     * If the value has changed since being loaded. Used for optimized saving.
     *
     * @return If changed.
     */
    boolean isChanged();

    /**
     * Resets to the old value.
     *
     * @return the newly set old value
     */
    T resetValue();
}
