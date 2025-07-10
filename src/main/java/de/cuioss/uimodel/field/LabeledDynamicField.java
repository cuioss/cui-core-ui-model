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
package de.cuioss.uimodel.field;

import java.io.Serializable;

/**
 * Extends {@link DynamicField} to add labeling and identification capabilities.
 * This interface is particularly useful for form fields that need to display
 * labels, advisory information, and maintain unique identifiers.
 *
 * <p>The interface provides:
 * <ul>
 *   <li>Localization keys for labels and advisory text</li>
 *   <li>Unique field identification</li>
 *   <li>All capabilities of {@link DynamicField}</li>
 * </ul>
 *
 * <p>This is commonly used for:
 * <ul>
 *   <li>Form input fields requiring labels</li>
 *   <li>Fields with help or advisory text</li>
 *   <li>Components needing unique identification for DOM manipulation</li>
 * </ul>
 *
 * @author Matthias Walliczek
 * @param <T> The type of value managed by this field. Must be {@link Serializable}
 *           to support persistence and distribution.
 * @since 1.0
 */
public interface LabeledDynamicField<T extends Serializable> extends DynamicField<T> {

    /**
     * Retrieves the key used for looking up the field's label text in a
     * resource bundle or message source.
     *
     * @return The key for label text lookup. May be null if no label
     *         is associated with this field.
     */
    String getLabelKey();

    /**
     * Retrieves the key used for looking up advisory or help text in a
     * resource bundle or message source. This text typically provides
     * additional guidance to users about the field's purpose or requirements.
     *
     * @return The key for advisory text lookup. May be null if no advisory
     *         text is associated with this field.
     */
    String getAdvisoryKey();

    /**
     * Retrieves the unique identifier for this field. This identifier can
     * be used for DOM element IDs, form field names, or other scenarios
     * requiring unique field identification.
     *
     * @return The unique identifier for this field. Should not be null.
     */
    String getIdentifier();
}
