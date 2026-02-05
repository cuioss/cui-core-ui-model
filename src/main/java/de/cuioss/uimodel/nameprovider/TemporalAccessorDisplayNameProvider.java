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
package de.cuioss.uimodel.nameprovider;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.time.temporal.TemporalAccessor;

/**
 * An implementation of {@link IDisplayNameProvider} specifically designed for
 * displaying temporal objects that implement {@link TemporalAccessor}. This
 * provider enables consistent handling of various date/time types in the UI.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Support for all TemporalAccessor implementations</li>
 *   <li>Immutable design</li>
 *   <li>Null-safe operations</li>
 *   <li>Proper equals/hashCode implementation</li>
 *   <li>Serialization support</li>
 * </ul>
 *
 * <p>Usage Examples:
 * <pre>
 * // With LocalDate
 * LocalDate date = LocalDate.now();
 * var provider = new TemporalAccessorDisplayNameProvider(date);
 *
 * // With LocalDateTime
 * LocalDateTime dateTime = LocalDateTime.now();
 * var provider = new TemporalAccessorDisplayNameProvider(dateTime);
 *
 * // With ZonedDateTime
 * ZonedDateTime zoned = ZonedDateTime.now();
 * var provider = new TemporalAccessorDisplayNameProvider(zoned);
 *
 * // With Instant
 * Instant instant = Instant.now();
 * var provider = new TemporalAccessorDisplayNameProvider(instant);
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Content cannot be null (enforced by {@link NonNull})</li>
 *   <li>All known TemporalAccessor implementations are Serializable</li>
 *   <li>Delegates actual formatting to the UI layer</li>
 *   <li>Thread-safe due to immutability</li>
 * </ul>
 *
 * <p>Common Use Cases:
 * <ul>
 *   <li>Date/time display in forms</li>
 *   <li>Timestamp presentation</li>
 *   <li>Calendar views</li>
 *   <li>Date/time selection components</li>
 *   <li>Audit trail displays</li>
 * </ul>
 *
 * <p>Supported Types:
 * <ul>
 *   <li>{@link java.time.LocalDate}</li>
 *   <li>{@link java.time.LocalTime}</li>
 *   <li>{@link java.time.LocalDateTime}</li>
 *   <li>{@link java.time.ZonedDateTime}</li>
 *   <li>{@link java.time.OffsetDateTime}</li>
 *   <li>{@link java.time.Instant}</li>
 *   <li>Any other {@link TemporalAccessor} implementation</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @see TemporalAccessor
 * @see java.time.temporal.Temporal
 * @see IDisplayNameProvider
 * @since 1.0
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@SuppressWarnings("squid:S1948") // owolff: all known implementation are Serializable
public class TemporalAccessorDisplayNameProvider implements IDisplayNameProvider<TemporalAccessor> {

    @Serial
    private static final long serialVersionUID = -1148355576864804121L;

    /**
     * The temporal object to be displayed. This field is guaranteed to be
     * non-null due to the {@link NonNull} annotation.
     *
     * <p>The actual formatting of this temporal object is delegated to the
     * UI layer, allowing for flexible display options based on context
     * and locale settings.
     */
    @Getter
    @NonNull
    private final TemporalAccessor content;
}
