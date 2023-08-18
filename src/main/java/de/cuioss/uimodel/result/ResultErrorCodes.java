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

import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.service.OptionalService;

/**
 * Default error codes for {@linkplain ResultObject} to be used like HTTP error
 * codes.
 */
public enum ResultErrorCodes {

    /**
     * The element to be retrieved could not be found with the given identifier
     */
    NOT_FOUND,

    /**
     * The user does not have the necessary privileges
     */
    NOT_AUTHORIZED,

    /**
     * The is not authenticated
     */
    NOT_AUTHENTICATED,

    /** The HttpRequest was not properly constructed. */
    BAD_REQUEST,

    /**
     * The {@linkplain OptionalService} is not available
     */
    SERVICE_NOT_AVAILABLE,

    /**
     * A general runtime error occurred. No further information available
     */
    RUNTIME_ERROR;

    private static final CuiLogger log = new CuiLogger(ResultErrorCodes.class);

    /**
     * Computes a concrete {@link ResultErrorCodes} from a given HttpCode
     *
     * @param httpCode
     * @return the matching {@link ResultErrorCodes} if it can be determined,
     *         {@link ResultErrorCodes#RUNTIME_ERROR} otherwise.
     */
    public static ResultErrorCodes parseHttpCode(int httpCode) {
        log.trace("Parsing ResultErrorCode from httpCode '{}'", httpCode);

        return switch (httpCode) {
        case 400 -> BAD_REQUEST;
        case 401 -> NOT_AUTHENTICATED;
        case 403 -> NOT_AUTHORIZED;
        case 404 -> NOT_FOUND;
        case 503 -> SERVICE_NOT_AVAILABLE;
        default -> RUNTIME_ERROR;
        };
    }
}
