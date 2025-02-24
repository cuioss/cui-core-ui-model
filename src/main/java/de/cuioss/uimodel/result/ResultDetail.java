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
package de.cuioss.uimodel.result;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Encapsulates detailed information about a result, particularly in error cases.
 * This class combines a display-friendly message with an optional cause, making
 * it suitable for both user interface display and technical error handling.
 *
 * <h2>Key Features</h2>
 * <ul>
 *   <li>Internationalization support via IDisplayNameProvider</li>
 *   <li>Optional exception cause tracking</li>
 *   <li>Immutable design</li>
 *   <li>Builder pattern support</li>
 *   <li>Serializable for distributed systems</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>1. Basic Error Detail</h3>
 * <pre>
 * ResultDetail detail = new ResultDetail(
 *     new DisplayName("Operation failed"));
 * </pre>
 *
 * <h3>2. Error with Cause</h3>
 * <pre>
 * try {
 *     // Some operation
 * } catch (ServiceException e) {
 *     ResultDetail detail = new ResultDetail(
 *         new DisplayName("Database connection failed"),
 *         e);
 * }
 * </pre>
 *
 * <h3>3. Using Builder Pattern</h3>
 * <pre>
 * ResultDetail detail = ResultDetail.builder()
 *     .detail(new DisplayName("Validation failed"))
 *     .cause(validationException)
 *     .build();
 * </pre>
 *
 * <h3>4. Internationalized Messages</h3>
 * <pre>
 * ResultDetail detail = new ResultDetail(
 *     new DisplayMessageProvider(
 *         "error.notfound",
 *         new Object[]{"User", userId}));
 * </pre>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Always provide meaningful display messages</li>
 *   <li>Include original exceptions as causes when available</li>
 *   <li>Use i18n-capable display providers for messages</li>
 *   <li>Consider security when exposing error details</li>
 *   <li>Keep messages user-friendly but informative</li>
 * </ul>
 *
 * <h2>Implementation Notes</h2>
 * <ul>
 *   <li>Thread-safe due to immutable design</li>
 *   <li>Equals/HashCode excludes cause for practical comparison</li>
 *   <li>Cause is optional but detail is mandatory</li>
 *   <li>Integrates with JSF message handling</li>
 *   <li>Supports Java 8 Optional API for cause</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see IDisplayNameProvider
 * @see ResultObject
 * @see de.cuioss.uimodel.nameprovider.DisplayMessageProvider
 * @since 1.0
 */
@EqualsAndHashCode(exclude = "cause")
@ToString
@Builder
public class ResultDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -6313940088570202322L;

    /**
     * The display provider that supplies the user-facing message for this detail.
     * This is typically an internationalized message that can be rendered
     * appropriately for the current locale.
     */
    @Getter
    private final IDisplayNameProvider<?> detail;

    /**
     * The underlying cause of this detail, typically an exception that
     * triggered the error condition. This is optional and may be null.
     */
    private final Throwable cause;

    /**
     * Creates a new ResultDetail with the specified display message.
     *
     * @param detail The display provider for the detail message, must not be {@code null}
     * @throws NullPointerException if detail is null
     */
    public ResultDetail(final IDisplayNameProvider<?> detail) {
        this(detail, null);
    }

    /**
     * Creates a new ResultDetail with the specified display message and cause.
     *
     * @param detail The display provider for the detail message, must not be {@code null}
     * @param cause The underlying cause of this detail, may be null
     * @throws NullPointerException if detail is null
     */
    public ResultDetail(final IDisplayNameProvider<?> detail, final Throwable cause) {
        this.cause = cause;
        this.detail = requireNonNull(detail, "detail");
    }

    /**
     * Returns the underlying cause of this detail, if one exists.
     *
     * @return An Optional containing the cause if present, or an empty Optional if no cause was set
     */
    public Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }
}
