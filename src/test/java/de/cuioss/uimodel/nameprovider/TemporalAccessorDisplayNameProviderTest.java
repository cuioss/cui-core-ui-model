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

import de.cuioss.test.valueobjects.ValueObjectTest;
import de.cuioss.test.valueobjects.api.contracts.VerifyConstructor;
import de.cuioss.test.valueobjects.api.generator.PropertyGenerator;
import de.cuioss.test.valueobjects.api.property.PropertyConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests TemporalAccessorDisplayNameProvider")
@PropertyConfig(name = "content", propertyClass = TemporalAccessor.class)
@PropertyGenerator(TemporalAccessorDisplayNameProviderTest.TemporalAccessorGenerator.class)
@VerifyConstructor(of = "content", required = "content")
class TemporalAccessorDisplayNameProviderTest extends ValueObjectTest<TemporalAccessorDisplayNameProvider> {

    public static class TemporalAccessorGenerator implements de.cuioss.test.generator.TypedGenerator<TemporalAccessor> {
        @Override
        public TemporalAccessor next() {
            return LocalDateTime.now();
        }

        @Override
        public Class<TemporalAccessor> getType() {
            return TemporalAccessor.class;
        }
    }

    @Test
    @DisplayName("Should handle LocalDate as shown in Javadoc")
    void shouldHandleLocalDate() {
        LocalDate date = LocalDate.now();
        var provider = new TemporalAccessorDisplayNameProvider(date);
        assertEquals(date, provider.getContent(), "LocalDate content should match");
    }

    @Test
    @DisplayName("Should handle LocalDateTime as shown in Javadoc")
    void shouldHandleLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        var provider = new TemporalAccessorDisplayNameProvider(dateTime);
        assertEquals(dateTime, provider.getContent(), "LocalDateTime content should match");
    }

    @Test
    @DisplayName("Should handle ZonedDateTime as shown in Javadoc")
    void shouldHandleZonedDateTime() {
        ZonedDateTime zoned = ZonedDateTime.now();
        var provider = new TemporalAccessorDisplayNameProvider(zoned);
        assertEquals(zoned, provider.getContent(), "ZonedDateTime content should match");
    }

    @Test
    @DisplayName("Should handle Instant as shown in Javadoc")
    void shouldHandleInstant() {
        Instant instant = Instant.now();
        var provider = new TemporalAccessorDisplayNameProvider(instant);
        assertEquals(instant, provider.getContent(), "Instant content should match");
    }

    @Test
    @DisplayName("Should reject null content")
    void shouldRejectNullContent() {
        var exception = assertThrows(NullPointerException.class,
                () -> new TemporalAccessorDisplayNameProvider(null),
                "Should throw NullPointerException for null content");
        assertEquals("content is marked non-null but is null", exception.getMessage(), "Exception message should match");
    }
}
