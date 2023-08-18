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

import static de.cuioss.tools.collect.CollectionLiterals.immutableSet;

import java.util.Set;

/**
 * Request result state, signaled if something special was happen on request.
 *
 * @author Eugen Fischer
 */
public enum ResultState {

    /**
     * signaled valid result
     */
    VALID,

    /**
     * signaled include info details
     */
    INFO,

    /**
     * signaled include warning details
     */
    WARNING,

    /**
     * signaled include error details, may additional error handling needed
     */
    ERROR;

    /**
     * Collection of request result states which must be handled
     */
    public static final Set<ResultState> MUST_BE_HANDLED = immutableSet(ERROR);
}
