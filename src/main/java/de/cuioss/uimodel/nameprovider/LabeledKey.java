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

import static de.cuioss.tools.string.MoreStrings.emptyToNull;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;

/**
 * Instance of IDisplayNameProvider which indicates to provide display key that
 * has a mapping to resource bundle key.
 *
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
@Builder
@Value
public class LabeledKey implements IDisplayNameProvider<String> {

    private static final long serialVersionUID = 4766238897848300167L;

    @Getter
    private final String content;

    /**
     * Optional Parameter-list to be passed to the label-resolving mechanism. May be
     * empty but never null
     */
    @Getter
    @Singular("parameter")
    private final List<Serializable> parameter;

    /**
     * @param labelKey must not be null nor empty.
     */
    public LabeledKey(final String labelKey) {
        this(labelKey, Collections.emptyList());
    }

    /**
     * @param labelKey  must not be null.
     * @param parameter One or more parameter to be passed to the label-resolving
     */
    public LabeledKey(final String labelKey, Serializable... parameter) {
        this(labelKey, asList(parameter));
    }

    /**
     * @param labelKey  must not be null.
     * @param parameter An optional List of parameter to be passed to the
     *                  label-resolving
     */
    public LabeledKey(final String labelKey, List<Serializable> parameter) {
        content = requireNonNull(emptyToNull(labelKey), "Key identifier must not be null");
        if (null == parameter) {
            this.parameter = Collections.emptyList();
        } else {
            this.parameter = parameter;
        }

    }
}
