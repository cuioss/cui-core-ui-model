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
package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Extends {@link TracedDynamicField} to provide a field that is read-only by default
 * but can be temporarily unlocked for editing. This pattern is useful for implementing
 * protected or sensitive fields that require explicit user action before modification.
 *
 * <p>Key features:
 * <ul>
 *   <li>Default read-only state for data protection</li>
 *   <li>Explicit unlocking mechanism for editing</li>
 *   <li>State tracking for edit mode</li>
 *   <li>Ability to reset to read-only state</li>
 * </ul>
 *
 * <p>Common use cases:
 * <ul>
 *   <li>Protected configuration settings</li>
 *   <li>Sensitive user data fields</li>
 *   <li>Fields requiring confirmation before editing</li>
 *   <li>Two-step modification processes</li>
 * </ul>
 *
 * @param <T> The type of value managed by this field. Must be {@link Serializable}
 *            to support persistence and distribution.
 * @author Eugen Fischer
 * @since 1.0
 */
public interface UnlockableTracedDynamicField<T extends Serializable> extends TracedDynamicField<T> {

    /**
     * Unlocks the field for editing by enabling edit mode. This allows modifications
     * to the field's value that would otherwise be prohibited in the default
     * read-only state.
     *
     * <p>After calling this method:
     * <ul>
     *   <li>The field becomes editable</li>
     *   <li>{@link #isEditModeEnforced()} will return true</li>
     *   <li>Value modifications through {@link TracedDynamicField#setValue(Serializable)} are allowed</li>
     * </ul>
     */
    void unlockEditMode();

    /**
     * Checks whether the field is currently in an unlocked edit mode state.
     * This can be used to determine if the field has been explicitly unlocked
     * for editing.
     *
     * @return {@code true} if edit mode has been enforced through {@link #unlockEditMode()},
     * {@code false} if the field is in its default read-only state
     */
    boolean isEditModeEnforced();

    /**
     * Resets the field back to its default read-only state, removing any temporary
     * edit permissions. This effectively "locks" the field again after it has been
     * unlocked.
     *
     * <p>After calling this method:
     * <ul>
     *   <li>The field returns to read-only state</li>
     *   <li>{@link #isEditModeEnforced()} will return false</li>
     *   <li>Value modifications will be prohibited until unlocked again</li>
     * </ul>
     */
    void resetEditMode();
}
