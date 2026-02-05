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
package de.cuioss.uimodel.result;

import java.util.Set;

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

/**
 * Defines the possible states of a {@link ResultObject}, indicating the outcome
 * of an operation and any associated information or error conditions. This enum
 * is central to the result handling framework's state management.
 *
 * <h2>States Overview</h2>
 * <ul>
 *   <li>{@link #VALID} - Operation completed successfully</li>
 *   <li>{@link #INFO} - Operation completed with additional information</li>
 *   <li>{@link #WARNING} - Operation completed but requires attention</li>
 *   <li>{@link #ERROR} - Operation failed and requires error handling</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>1. Basic State Handling</h3>
 * <pre>
 * ResultObject&lt;User&gt; result = service.findUser(id);
 * if (result.getState() == ResultState.VALID) {
 *     processUser(result.getResult());
 * } else if (result.getState() == ResultState.ERROR) {
 *     handleError(result.getResultDetail());
 * }
 * </pre>
 *
 * <h3>2. State-based Flow Control</h3>
 * <pre>
 * switch (result.getState()) {
 *     case VALID:
 *         processSuccess(result);
 *         break;
 *     case INFO:
 *         showInfo(result);
 *         processSuccess(result);
 *         break;
 *     case WARNING:
 *         showWarning(result);
 *         handleWithCaution(result);
 *         break;
 *     case ERROR:
 *         handleError(result);
 *         break;
 * }
 * </pre>
 *
 * <h2>State Characteristics</h2>
 * <ul>
 *   <li><strong>VALID</strong>
 *     <ul>
 *       <li>Operation completed successfully</li>
 *       <li>Result is available and trustworthy</li>
 *       <li>No error handling required</li>
 *     </ul>
 *   </li>
 *   <li><strong>INFO</strong>
 *     <ul>
 *       <li>Operation completed successfully</li>
 *       <li>Additional information available</li>
 *       <li>May require user notification</li>
 *     </ul>
 *   </li>
 *   <li><strong>WARNING</strong>
 *     <ul>
 *       <li>Operation completed but with concerns</li>
 *       <li>Result may need verification</li>
 *       <li>User attention recommended</li>
 *     </ul>
 *   </li>
 *   <li><strong>ERROR</strong>
 *     <ul>
 *       <li>Operation failed</li>
 *       <li>Error handling required</li>
 *       <li>Default result may be provided</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Always check state before accessing result</li>
 *   <li>Handle ERROR states explicitly</li>
 *   <li>Consider WARNING states for validation</li>
 *   <li>Use INFO for non-critical messages</li>
 *   <li>Provide appropriate UI feedback for each state</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see ResultObject
 * @see ResultDetail
 * @since 1.0
 */
public enum ResultState {

    /**
     * Indicates a successful operation with a valid result.
     * The result can be accessed safely and no additional handling is required.
     */
    VALID,

    /**
     * Indicates a successful operation with additional information.
     * The result is valid but there may be supplementary details that
     * should be communicated to the user.
     */
    INFO,

    /**
     * Indicates a completed operation that requires attention.
     * The result may be valid but there are conditions or limitations
     * that the user should be aware of.
     */
    WARNING,

    /**
     * Indicates a failed operation that requires error handling.
     * The result may not be valid or may be a fallback value.
     * Error details should be available and must be handled appropriately.
     */
    ERROR;

    /**
     * Defines the set of states that must be explicitly handled by the application.
     * Currently, this includes only the ERROR state, as it represents conditions
     * that cannot be safely ignored.
     */
    public static final Set<ResultState> MUST_BE_HANDLED = immutableSet(ERROR);
}
