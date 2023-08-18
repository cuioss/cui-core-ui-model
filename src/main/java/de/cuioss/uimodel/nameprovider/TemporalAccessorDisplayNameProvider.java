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

import java.time.temporal.TemporalAccessor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Displays instances of {@link TemporalAccessor}
 *
 */
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@SuppressWarnings("squid:S1948") // owolff: all known implementation are Serializable
public class TemporalAccessorDisplayNameProvider implements IDisplayNameProvider<TemporalAccessor> {

    private static final long serialVersionUID = -1148355576864804121L;

    @Getter
    @NonNull
    private final TemporalAccessor content;
}
