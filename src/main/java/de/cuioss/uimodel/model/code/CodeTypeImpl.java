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
package de.cuioss.uimodel.model.code;

import static java.util.Objects.requireNonNull;

import java.io.Serial;
import java.util.Locale;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Simple implementation of {@link CodeType} that ignores the {@link Locale}
 * attribute on {@link #getResolved(Locale)} and always returns a previously
 * configured string.
 *
 * @author Oliver Wolff
 */
@EqualsAndHashCode
@ToString
public class CodeTypeImpl implements CodeType {

    @Serial
    private static final long serialVersionUID = 6827791409255699288L;

    private final String resolved;

    @Getter
    private final String identifier;

    /**
     * @param resolved   must not be null
     * @param identifier must not be null
     */
    public CodeTypeImpl(final String resolved, final String identifier) {
        this.resolved = requireNonNull(resolved, "resolved must not be null");
        this.identifier = requireNonNull(identifier, "identifier must not be null");
    }

    /**
     * @param resolved to be used as resolved and identifier.
     */
    public CodeTypeImpl(final String resolved) {
        this(resolved, resolved);
    }

    @Override
    public String getResolved(final Locale locale) {
        return resolved;
    }

}
