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
 * A {@linkplain TracedDynamicField} that is read-only per default, but can be
 * unlocked upon demand.
 *
 * @author Eugen Fischer
 * @param <T> bounded type must be {@link Serializable}
 */
public interface UnlockableTracedDynamicField<T extends Serializable> extends TracedDynamicField<T> {

    /**
     * execute action unlock edit mode
     */
    void unlockEditMode();

    /**
     * Retrieve information if edit mode was enforced.
     *
     * @return {@code true} if edit mode was overwritten, {@code false} otherwise
     */
    boolean isEditModeEnforced();

    /**
     * execute action to reset edit mode
     */
    void resetEditMode();
}
