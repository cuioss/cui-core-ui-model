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
 * An optional service is a service that may be accessible or not. This usually
 * defined by the concrete configuration or at runtime, e.g. if a remote service
 * is not available
 *
 * @author Oliver Wolff
 *
 */
public interface OptionalService {

    /**
     * @return boolean indicating whether the service is currently enabled or not.
     */
    default ServiceState getServiceState() {
        return ServiceState.ACTIVE;
    }

    /**
     * Shortcut for checking whether the service is available for the current call
     * context.
     *
     * @return boolean indicating that the service is up and running and accessible
     *         for the current logged in user
     */
    default boolean isServiceAvailable() {
        return ServiceState.ACTIVE.equals(getServiceState());
    }
}
