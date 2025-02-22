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
package de.cuioss.uimodel.nameprovider;

import de.cuioss.uimodel.model.code.CodeType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;

/**
 * An implementation of {@link IDisplayNameProvider} designed to handle
 * {@link CodeType} objects. This provider enables consistent display of
 * code-based types in the UI layer.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Immutable design</li>
 *   <li>Null-safe operations</li>
 *   <li>Type-safe CodeType handling</li>
 *   <li>Proper equals/hashCode implementation</li>
 *   <li>Serialization support</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // With a simple CodeType implementation
 * CodeType statusCode = new SimpleCodeType("ACTIVE", "user.status.active");
 * var provider = new CodeTypeDisplayNameProvider(statusCode);
 *
 * // With an enum implementing CodeType
 * public enum UserStatus implements CodeType {
 *     ACTIVE("user.status.active"),
 *     INACTIVE("user.status.inactive");
 *     
 *     private final String labelKey;
 *     
 *     UserStatus(String labelKey) {
 *         this.labelKey = labelKey;
 *     }
 *     
 *     public String getIdentifier() {
 *         return name();
 *     }
 *     
 *     public String getLabelKey() {
 *         return labelKey;
 *     }
 * }
 * var provider = new CodeTypeDisplayNameProvider(UserStatus.ACTIVE);
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Content cannot be null (enforced by {@link NonNull})</li>
 *   <li>Delegates display logic to the CodeType implementation</li>
 *   <li>Thread-safe due to immutability</li>
 *   <li>Suitable for use with enums and classes</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Status indicators</li>
 *   <li>Type selectors</li>
 *   <li>Category displays</li>
 *   <li>Enumeration presentation</li>
 *   <li>Code-based dropdowns</li>
 * </ul>
 *
 * <p>Integration Points:
 * <ul>
 *   <li>Works with any {@link CodeType} implementation</li>
 *   <li>Compatible with resource bundle lookups</li>
 *   <li>Suitable for JSF component integration</li>
 *   <li>Supports internationalization</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see CodeType
 * @see IDisplayNameProvider
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class CodeTypeDisplayNameProvider implements IDisplayNameProvider<CodeType> {

    @Serial
    private static final long serialVersionUID = -4376110565562089715L;

    /**
     * The CodeType object to be displayed. This field is guaranteed to be
     * non-null due to the {@link NonNull} annotation.
     *
     * <p>The actual display logic is delegated to the CodeType implementation,
     * which typically provides:
     * <ul>
     *   <li>An identifier (via {@link CodeType#getIdentifier()})</li>
     *   <li>A label key (via {@link CodeType#getLabelKey()})</li>
     *   <li>Optional sorting information</li>
     * </ul>
     */
    @Getter
    @NonNull
    private final CodeType content;

}
