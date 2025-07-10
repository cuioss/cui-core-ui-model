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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * A simple implementation of {@link IDisplayNameProvider} that provides direct,
 * non-resolving string content for display. This class is particularly useful
 * when you have static text that doesn't require any transformation or resolution.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Immutable design</li>
 *   <li>Null-safe content handling</li>
 *   <li>Direct string content</li>
 *   <li>No content resolution needed</li>
 *   <li>Proper equals/hashCode implementation</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Basic usage
 * DisplayName name = new DisplayName("John Doe");
 * String display = name.getContent(); // Returns "John Doe"
 *
 * // Using in UI components
 * uiComponent.setValue(new DisplayName("Click me"));
 *
 * // Collections
 * List&lt;IDisplayNameProvider&lt;String&gt;&gt; names = Arrays.asList(
 *     new DisplayName("First"),
 *     new DisplayName("Second")
 * );
 *
 * // Comparison
 * DisplayName name1 = new DisplayName("Test");
 * DisplayName name2 = new DisplayName("Test");
 * boolean equals = name1.equals(name2); // true
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Content cannot be null (enforced by {@link NonNull})</li>
 *   <li>Content cannot be changed after creation</li>
 *   <li>Implements proper toString for debugging</li>
 *   <li>Suitable for use in collections (proper equals/hashCode)</li>
 *   <li>Serializable for distributed environments</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Static labels</li>
 *   <li>Button text</li>
 *   <li>Fixed headers</li>
 *   <li>Constant messages</li>
 *   <li>Pre-formatted text</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @since 1.0
 * @see IDisplayNameProvider
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class DisplayName implements IDisplayNameProvider<String> {

    @Serial
    private static final long serialVersionUID = -5827322353134669923L;

    /**
     * The immutable string content to be displayed. This value is guaranteed
     * to be non-null due to {@link NonNull} annotation.
     *
     * <p>The content:
     * <ul>
     *   <li>Cannot be null</li>
     *   <li>Is immutable</li>
     *   <li>Is directly used for display</li>
     *   <li>Requires no transformation</li>
     * </ul>
     */
    @Getter
    @NonNull
    private final String content;

}
