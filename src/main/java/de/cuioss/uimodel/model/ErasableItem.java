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
package de.cuioss.uimodel.model;

import java.io.Serializable;

/**
 * Defines a contract for items that can be marked for deletion without being
 * immediately removed from the system. This pattern is useful for implementing
 * soft-delete functionality, undo/redo operations, or staged deletions that
 * require confirmation.
 *
 * <p>Features:
 * <ul>
 *   <li>Soft-delete state management</li>
 *   <li>Toggle capability for delete state</li>
 *   <li>Serializable for state persistence</li>
 * </ul>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Implementations should maintain the delete flag in a thread-safe manner</li>
 *   <li>The delete state should be considered in equals/hashCode if relevant</li>
 *   <li>Consider adding timestamp or user information for audit purposes</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * public class DeletableDocument implements ErasableItem {
 *     private boolean deleted = false;
 *     private final String content;
 *
 *     &#64;Override
 *     public boolean isMarkedAsDeleted() {
 *         return deleted;
 *     }
 *
 *     &#64;Override
 *     public void toggleDeleteFlag() {
 *         deleted = !deleted;
 *     }
 *
 *     public void processIfNotDeleted() {
 *         if (!isMarkedAsDeleted()) {
 *             // Process document
 *         }
 *     }
 * }
 * </pre>
 *
 * @author Eugen Fischer
 * @since 1.0
 */
public interface ErasableItem extends Serializable {

    /**
     * Checks if this item is currently marked for deletion.
     *
     * @return {@code true} if the item is marked for deletion,
     *         {@code false} otherwise
     */
    boolean isMarkedAsDeleted();

    /**
     * Toggles the deletion state of this item. If the item is currently
     * marked as deleted, it will be unmarked, and vice versa.
     *
     * <p>This method provides a convenient way to flip the delete flag
     * without explicitly tracking its current state externally.
     */
    void toggleDeleteFlag();
}
