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
import lombok.ToString;

import java.io.Serial;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static java.util.Objects.requireNonNull;

/**
 * A provider class that implements {@link IDisplayNameProvider} to handle
 * message formatting with resource bundles. This class acts as a bridge between
 * {@link DisplayMessageFormat} and the actual message resolution/formatting process.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Resource bundle integration</li>
 *   <li>Message formatting support</li>
 *   <li>Type-safe content handling</li>
 *   <li>Builder pattern support</li>
 *   <li>Proper error handling</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // Direct construction
 * DisplayMessageFormat format = new DisplayMessageFormat("greeting", "John");
 * DisplayMessageProvider provider = new DisplayMessageProvider(format);
 *
 * // Using builder pattern
 * DisplayMessageProvider provider = new DisplayMessageProvider.Builder()
 *     .messageKey("welcome")
 *     .add("Admin")
 *     .add(5)
 *     .build();
 *
 * // Formatting with resource bundle
 * ResourceBundle bundle = ResourceBundle.getBundle("messages");
 * String result = provider.getMessageFormated(bundle);
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Thread-safe and immutable</li>
 *   <li>Null-safe operations</li>
 *   <li>Proper exception handling</li>
 *   <li>Resource bundle integration</li>
 *   <li>MessageFormat compatibility</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Internationalized UI messages</li>
 *   <li>Parameterized error messages</li>
 *   <li>Dynamic content formatting</li>
 *   <li>Resource bundle integration</li>
 *   <li>Message templating</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see IDisplayNameProvider
 * @see DisplayMessageFormat
 * @see MessageFormat
 * @see ResourceBundle
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
public class DisplayMessageProvider implements IDisplayNameProvider<DisplayMessageFormat> {

    @Serial
    private static final long serialVersionUID = -3453598477657055961L;

    /**
     * The message format content that this provider manages.
     * This field is final and immutable.
     */
    private final DisplayMessageFormat content;

    /**
     * Creates a new DisplayMessageProvider with the specified message format.
     *
     * @param displayMessageFormat the format to use, must not be {@code null}
     * @throws NullPointerException if displayMessageFormat is null
     */
    public DisplayMessageProvider(final DisplayMessageFormat displayMessageFormat) {
        content = requireNonNull(displayMessageFormat, "displayMessageFormat");
    }

    /**
     * Returns the underlying message format content.
     *
     * @return the message format content, never null
     */
    @Override
    public DisplayMessageFormat getContent() {
        return content;
    }

    /**
     * Formats the message using the provided resource bundle and the stored
     * message format content.
     *
     * <p>The process:
     * <ol>
     *   <li>Looks up the message pattern using the key from content</li>
     *   <li>Applies the arguments to the pattern using MessageFormat</li>
     *   <li>Returns the formatted result</li>
     * </ol>
     *
     * @param bundle the resource bundle to use for message lookup
     * @return the formatted message string
     * @throws MissingResourceException if no object for the given key can be found
     * @throws IllegalArgumentException if:
     *                                  <ul>
     *                                    <li>bundle is null</li>
     *                                    <li>the pattern is invalid</li>
     *                                    <li>an argument type doesn't match the format element</li>
     *                                  </ul>
     */
    public String getMessageFormated(final ResourceBundle bundle) {
        requireNonNull(bundle, "bundle");
        return MessageFormat.format(bundle.getString(content.getMsgKey()),
                content.getArguments().toArray(new Object[content.getArguments().size()]));
    }

    /**
     * Builder class that provides a fluent API for creating
     * {@link DisplayMessageProvider} instances. This builder delegates to
     * {@link DisplayMessageFormat.Builder} for the actual construction.
     *
     * <p>Usage example:
     * <pre>
     * DisplayMessageProvider provider = new DisplayMessageProvider.Builder()
     *     .messageKey("welcome")
     *     .add("User")
     *     .build();
     * </pre>
     */
    public static class Builder {

        /**
         * Starts building a new DisplayMessageProvider by creating a
         * DisplayMessageFormat builder with the specified message key.
         *
         * @param messageKey the message key to use, must not be null or empty
         * @return a new DisplayMessageFormat builder
         * @throws IllegalArgumentException if messageKey is null or empty
         */
        public DisplayMessageFormat.Builder messageKey(final String messageKey) {
            return new DisplayMessageFormat.Builder(messageKey);
        }
    }
}
