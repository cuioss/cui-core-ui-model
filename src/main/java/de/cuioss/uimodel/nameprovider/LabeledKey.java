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
package de.cuioss.uimodel.nameprovider;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

/**
 * An implementation of {@link IDisplayNameProvider} that represents a key-based
 * label with optional parameters. This class is designed to work with resource
 * bundles for internationalization (i18n) support.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Immutable value object</li>
 *   <li>Resource bundle key support</li>
 *   <li>Optional parameter list</li>
 *   <li>Builder pattern support</li>
 *   <li>Null-safe operations</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Simple key
 * LabeledKey simple = new LabeledKey("button.save");
 *
 * // Key with parameters
 * LabeledKey withParams = new LabeledKey("user.greeting", "John", 42);
 *
 * // Using builder
 * LabeledKey built = LabeledKey.builder()
 *     .content("dialog.title")
 *     .parameter("Document")
 *     .parameter(123)
 *     .build();
 *
 * // Getting content for resource bundle lookup
 * String key = labeledKey.getContent();
 * List&lt;Serializable&gt; params = labeledKey.getParameter();
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Content key cannot be null or empty</li>
 *   <li>Parameter list is never null (empty list if no parameters)</li>
 *   <li>All fields are immutable</li>
 *   <li>Implements proper equals/hashCode</li>
 *   <li>Provides comprehensive toString</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>UI labels and messages</li>
 *   <li>Internationalized content</li>
 *   <li>Parameterized messages</li>
 *   <li>Button/menu labels</li>
 *   <li>Error messages</li>
 * </ul>
 *
 * <p>Differences from {@link DisplayMessageFormat}:
 * <ul>
 *   <li>LabeledKey is simpler, focusing only on key-based lookups</li>
 *   <li>Parameters are optional and handled by the resolver</li>
 *   <li>More suitable for simple UI labels</li>
 *   <li>No direct formatting capabilities</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see IDisplayNameProvider
 * @see LabelKeyProvider
 */
@ToString
@EqualsAndHashCode
@Builder
@Value
public class LabeledKey implements IDisplayNameProvider<String> {

    @Serial
    private static final long serialVersionUID = 4766238897848300167L;

    /**
     * The resource bundle key that identifies this label.
     * This key is used for looking up the actual text in a resource bundle.
     */
    @Getter
    private final String content;

    /**
     * Optional parameters that can be used during label resolution.
     * These parameters might be used for placeholder replacement or
     * other transformations during the resolution process.
     *
     * <p>Implementation Notes:
     * <ul>
     *   <li>Never null (empty list if no parameters)</li>
     *   <li>Immutable list</li>
     *   <li>Elements must be serializable</li>
     *   <li>Order of parameters is preserved</li>
     * </ul>
     */
    @Getter
    @Singular("parameter")
    private final List<Serializable> parameter;

    /**
     * Creates a new LabeledKey with just a key and no parameters.
     *
     * @param labelKey the resource bundle key, must not be null or empty
     * @throws IllegalArgumentException if labelKey is null or empty
     */
    public LabeledKey(final String labelKey) {
        this(labelKey, Collections.emptyList());
    }

    /**
     * Creates a new LabeledKey with a key and variable number of parameters.
     *
     * @param labelKey the resource bundle key, must not be null or empty
     * @param parameter optional parameters for label resolution
     * @throws IllegalArgumentException if labelKey is null or empty
     */
    public LabeledKey(final String labelKey, Serializable... parameter) {
        this(labelKey, asList(parameter));
    }

    /**
     * Creates a new LabeledKey with a key and a list of parameters.
     *
     * @param labelKey the resource bundle key, must not be null or empty
     * @param parameter optional list of parameters for label resolution,
     *                 if null an empty list will be used
     * @throws IllegalArgumentException if labelKey is null or empty
     */
    public LabeledKey(final String labelKey, List<Serializable> parameter) {
        content = requireNonNull(emptyToNull(labelKey), "Key identifier must not be null");
        if (null == parameter) {
            this.parameter = Collections.emptyList();
        } else {
            this.parameter = parameter;
        }
    }
}
