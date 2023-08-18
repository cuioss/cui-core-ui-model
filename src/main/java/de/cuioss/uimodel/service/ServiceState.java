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
package de.cuioss.uimodel.service;

/**
 * Indicates the state / availability of a service
 *
 * @author Oliver Wolff
 *
 */
public enum ServiceState {

    /** The service is active and can be accessed. */
    ACTIVE,

    /** Indicates the service is configured but currently not available. */
    TEMPORARILY_UNAVAILABLE,

    /**
     * Indicates that the service is not configured at all or the configuration is
     * incomplete or invalid.
     */
    NOT_CONFIGURED,

    /**
     * Indicates that the service is {@link #ACTIVE} but can can not be used by the
     * currently logged in user
     */
    NOT_AVAILABLE_FOR_USER
}
