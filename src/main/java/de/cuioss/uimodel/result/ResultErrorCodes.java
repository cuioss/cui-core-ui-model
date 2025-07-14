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
package de.cuioss.uimodel.result;

import de.cuioss.tools.logging.CuiLogger;
import de.cuioss.uimodel.service.OptionalService;

/**
 * Standardized error codes for {@link ResultObject} implementations, inspired by
 * HTTP status codes. These codes provide a consistent way to categorize and handle
 * common error scenarios across the application.
 *
 * <h2>Available Error Codes</h2>
 * <ul>
 *   <li>{@link #NOT_FOUND} - Resource not found (HTTP 404)</li>
 *   <li>{@link #NOT_AUTHORIZED} - Insufficient privileges (HTTP 403)</li>
 *   <li>{@link #NOT_AUTHENTICATED} - Authentication required (HTTP 401)</li>
 *   <li>{@link #BAD_REQUEST} - Invalid request (HTTP 400)</li>
 *   <li>{@link #SERVICE_NOT_AVAILABLE} - Service unavailable (HTTP 503)</li>
 *   <li>{@link #RUNTIME_ERROR} - General error (fallback)</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>1. Basic Error Code Usage</h3>
 * <pre>
 * public ResultObject&lt;User&gt; findUser(String id) {
 *     try {
 *         User user = repository.find(id);
 *         if (user == null) {
 *             return ResultObject.error(
 *                 new ResultDetail(new DisplayName("User not found")),
 *                 ResultErrorCodes.NOT_FOUND
 *             );
 *         }
 *         return ResultObject.success(user);
 *     } catch (SecurityException e) {
 *         return ResultObject.error(
 *             new ResultDetail(new DisplayName("Access denied")),
 *             ResultErrorCodes.NOT_AUTHORIZED
 *         );
 *     }
 * }
 * </pre>
 *
 * <h3>2. HTTP Integration</h3>
 * <pre>
 * public ResultObject&lt;Resource&gt; handleHttpResponse(HttpResponse response) {
 *     if (!response.isSuccessful()) {
 *         ResultErrorCodes errorCode = ResultErrorCodes.parseHttpCode(
 *             response.getStatusCode()
 *         );
 *         return ResultObject.error(
 *             new ResultDetail(new DisplayName(response.getMessage())),
 *             errorCode
 *         );
 *     }
 *     return ResultObject.success(response.getBody());
 * }
 * </pre>
 *
 * <h2>Error Code Categories</h2>
 *
 * <h3>Client Errors</h3>
 * <ul>
 *   <li>{@code BAD_REQUEST} - Client-side validation failures</li>
 *   <li>{@code NOT_FOUND} - Requested resource doesn't exist</li>
 * </ul>
 *
 * <h3>Security Errors</h3>
 * <ul>
 *   <li>{@code NOT_AUTHENTICATED} - User identity not established</li>
 *   <li>{@code NOT_AUTHORIZED} - Insufficient permissions</li>
 * </ul>
 *
 * <h3>System Errors</h3>
 * <ul>
 *   <li>{@code SERVICE_NOT_AVAILABLE} - Service/system unavailable</li>
 *   <li>{@code RUNTIME_ERROR} - Unspecified system errors</li>
 * </ul>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Use specific error codes when possible</li>
 *   <li>Reserve RUNTIME_ERROR for truly unexpected cases</li>
 *   <li>Include detailed messages with error codes</li>
 *   <li>Consider security implications when choosing codes</li>
 *   <li>Maintain consistency with HTTP semantics</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see ResultObject
 * @see ResultDetail
 * @since 1.0
 */
public enum ResultErrorCodes {

    /**
     * Indicates that the requested resource could not be found.
     * Corresponds to HTTP status code 404.
     *
     * <p>Use when:
     * <ul>
     *   <li>Entity lookup fails</li>
     *   <li>Resource is not available</li>
     *   <li>Path or identifier is invalid</li>
     * </ul>
     */
    NOT_FOUND,

    /**
     * Indicates that the user lacks the necessary privileges to perform
     * the requested operation. Corresponds to HTTP status code 403.
     *
     * <p>Use when:
     * <ul>
     *   <li>User lacks required roles</li>
     *   <li>Access is forbidden</li>
     *   <li>Resource is protected</li>
     * </ul>
     */
    NOT_AUTHORIZED,

    /**
     * Indicates that authentication is required to access the resource.
     * Corresponds to HTTP status code 401.
     *
     * <p>Use when:
     * <ul>
     *   <li>User is not logged in</li>
     *   <li>Session has expired</li>
     *   <li>Credentials are missing</li>
     * </ul>
     */
    NOT_AUTHENTICATED,

    /**
     * Indicates that the request was malformed or invalid.
     * Corresponds to HTTP status code 400.
     *
     * <p>Use when:
     * <ul>
     *   <li>Input validation fails</li>
     *   <li>Required parameters are missing</li>
     *   <li>Data format is incorrect</li>
     * </ul>
     */
    BAD_REQUEST,

    /**
     * Indicates that a required service is not available.
     * Corresponds to HTTP status code 503.
     *
     * <p>Use when:
     * <ul>
     *   <li>External service is down</li>
     *   <li>System is in maintenance</li>
     *   <li>Resources are temporarily unavailable</li>
     * </ul>
     *
     * @see OptionalService
     */
    SERVICE_NOT_AVAILABLE,

    /**
     * Indicates a general runtime error with no specific categorization.
     * This is a fallback error code for unexpected situations.
     *
     * <p>Use when:
     * <ul>
     *   <li>Unexpected exceptions occur</li>
     *   <li>System is in an inconsistent state</li>
     *   <li>No other error code applies</li>
     * </ul>
     */
    RUNTIME_ERROR;

    private static final CuiLogger LOGGER = new CuiLogger(ResultErrorCodes.class);

    /**
     * Maps an HTTP status code to the corresponding {@link ResultErrorCodes}.
     * This method provides integration with HTTP-based services by converting
     * standard HTTP status codes to application-specific error codes.
     *
     * <p>Mapping:
     * <ul>
     *   <li>400 → {@link #BAD_REQUEST}</li>
     *   <li>401 → {@link #NOT_AUTHENTICATED}</li>
     *   <li>403 → {@link #NOT_AUTHORIZED}</li>
     *   <li>404 → {@link #NOT_FOUND}</li>
     *   <li>503 → {@link #SERVICE_NOT_AVAILABLE}</li>
     *   <li>others → {@link #RUNTIME_ERROR}</li>
     * </ul>
     *
     * @param httpCode The HTTP status code to convert
     * @return The corresponding ResultErrorCode
     */
    public static ResultErrorCodes parseHttpCode(int httpCode) {
        LOGGER.trace("Parsing ResultErrorCode from httpCode '%s'", httpCode);

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
