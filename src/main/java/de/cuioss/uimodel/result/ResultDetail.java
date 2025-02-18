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

import static java.util.Objects.requireNonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;

import de.cuioss.uimodel.nameprovider.IDisplayNameProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Request Result Detail
 *
 * @author Eugen Fischer
 */
@EqualsAndHashCode(exclude = "cause")
@ToString
@Builder
public class ResultDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = -6313940088570202322L;

    @Getter
    private final IDisplayNameProvider<?> detail;

    private final Throwable cause;

    /**
     * @param detail any {@linkplain IDisplayNameProvider} must not be {@code null}
     */
    public ResultDetail(final IDisplayNameProvider<?> detail) {
        this(detail, null);
    }

    /**
     * @param detail any {@linkplain IDisplayNameProvider} must not be {@code null}
     * @param cause  any Throwable
     */
    public ResultDetail(final IDisplayNameProvider<?> detail, final Throwable cause) {
        this.cause = cause;
        this.detail = requireNonNull(detail, "detail");
    }

    /**
     * Non mandatory
     *
     * @return the optional cause
     */
    public Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }

}
