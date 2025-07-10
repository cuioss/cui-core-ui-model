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

import de.cuioss.tools.logging.CuiLogger;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.cuioss.tools.collect.CollectionLiterals.immutableList;
import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static de.cuioss.tools.string.MoreStrings.requireNotEmpty;
import static de.cuioss.uimodel.UiModelLogMessages.WARN;
import static java.util.Objects.requireNonNull;

/**
 * Provides a structured way to handle message formatting with keys and arguments,
 * designed to work with {@link java.text.MessageFormat}. This class enables
 * type-safe message construction with support for parameterized messages.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Immutable message format structure</li>
 *   <li>Type-safe argument handling</li>
 *   <li>Null-safe operations</li>
 *   <li>Builder pattern support</li>
 *   <li>Serialization support</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Direct construction
 * DisplayMessageFormat simple = new DisplayMessageFormat(
 *     "user.greeting",
 *     "John", "Doe"
 * );
 *
 * // Using builder pattern
 * DisplayMessageProvider provider = DisplayMessageFormat.builder()
 *     .msgKey("user.profile")
 *     .add("John")
 *     .add(42)
 *     .build();
 *
 * // With list of arguments
 * List&lt;Serializable&gt; args = Arrays.asList("Admin", 100);
 * DisplayMessageFormat withList = new DisplayMessageFormat(
 *     "admin.status",
 *     args
 * );
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Message keys cannot be null or empty</li>
 *   <li>Arguments list is immutable after creation</li>
 *   <li>Null arguments are handled gracefully</li>
 *   <li>Builder validates inputs before construction</li>
 *   <li>All components are serializable</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Internationalized messages</li>
 *   <li>Parameterized system messages</li>
 *   <li>User notifications</li>
 *   <li>Audit logging</li>
 *   <li>Error messages</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see java.text.MessageFormat
 * @see DisplayMessageProvider
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
public class DisplayMessageFormat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1169472475051755852L;

    /**
     * The message key used to look up the message template.
     * This key must not be null or empty.
     */
    @Getter
    private final String msgKey;

    /**
     * The list of arguments to be used in message formatting.
     * The list is immutable and may be empty but not null.
     */
    @Getter
    private final List<Serializable> arguments;

    /**
     * Creates a new DisplayMessageFormat with the specified message key and
     * list of arguments.
     *
     * @param messageKey       must not be {@code null} or empty
     * @param messageArguments must not be {@code null}, but may be empty
     * @throws IllegalArgumentException if messageKey is null or empty
     * @throws NullPointerException     if messageArguments is null
     */
    public DisplayMessageFormat(final String messageKey, final List<Serializable> messageArguments) {
        msgKey = requireNotEmpty(messageKey, "messageKey");
        arguments = requireNonNull(messageArguments);
    }

    /**
     * Creates a new DisplayMessageFormat with the specified message key and
     * variable argument list.
     * This is a convenience constructor that converts the varargs to an immutable list.
     *
     * @param messageKey       must not be {@code null} or empty
     * @param messageArguments optional array of arguments, may be null or empty
     * @throws IllegalArgumentException if messageKey is null or empty
     */
    public DisplayMessageFormat(final String messageKey, final Serializable... messageArguments) {
        this(messageKey, immutableList(messageArguments));
    }

    /**
     * Builder class for creating {@link DisplayMessageProvider} instances.
     * Provides a fluent API for constructing message providers with proper
     * validation and error handling.
     *
     * <p>Features:
     * <ul>
     *   <li>Fluent API design</li>
     *   <li>Null-safe argument handling</li>
     *   <li>Input validation</li>
     *   <li>Proper error messaging</li>
     * </ul>
     */
    @NoArgsConstructor
    public static class Builder {

        private static final CuiLogger LOGGER = new CuiLogger(DisplayMessageFormat.Builder.class);

        private String tempMsgKey;

        private final List<Serializable> tempArguments = new ArrayList<>();

        /**
         * Creates a new builder with an initial message key.
         *
         * @param messageKey message bundle key, may be null or empty
         */
        public Builder(final String messageKey) {
            tempMsgKey = emptyToNull(messageKey);
        }

        /**
         * Adds variable arguments to the message format.
         * Null arguments are silently ignored.
         *
         * @param arguments optional arguments to add, may be null
         * @return this builder for method chaining
         */
        public Builder add(final Serializable... arguments) {
            if (null != arguments) {
                addAll(Arrays.asList(arguments));
            }
            return this;
        }

        /**
         * Sets or updates the message key.
         *
         * @param msgKey the new message key, may be null or empty
         * @return this builder for method chaining
         */
        public Builder msgKey(final String msgKey) {
            tempMsgKey = emptyToNull(msgKey);
            return this;
        }

        /**
         * Adds a list of arguments to the message format.
         * Null entries in the list are silently ignored.
         *
         * @param arguments list of arguments to add, may be null
         * @return this builder for method chaining
         */
        public Builder addAll(final List<Serializable> arguments) {
            if (null != arguments) {
                for (final Serializable entry : arguments) {
                    if (null != entry) {
                        tempArguments.add(entry);
                    }
                }
            }
            return this;
        }

        /**
         * Builds and returns a new {@link DisplayMessageProvider}.
         * Validates that the message key is not null before building.
         *
         * @return a new DisplayMessageProvider instance
         * @throws NullPointerException if message key is null
         */
        public DisplayMessageProvider build() {
            requireNonNull(tempMsgKey);
            if (tempArguments.isEmpty()) {
                LOGGER.warn(WARN.EMPTY_MESSAGE_ARGS::format);
            }
            return new DisplayMessageProvider(new DisplayMessageFormat(tempMsgKey, tempArguments));
        }
    }

    /**
     * Creates and returns a new builder instance for fluent construction
     * of DisplayMessageFormat objects.
     *
     * @return a new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}
