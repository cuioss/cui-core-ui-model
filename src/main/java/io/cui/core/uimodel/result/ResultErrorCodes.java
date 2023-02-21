package io.cui.core.uimodel.result;

import io.cui.core.uimodel.service.OptionalService;
import io.cui.tools.logging.CuiLogger;

/**
 * Default error codes for {@linkplain ResultObject} to be used like HTTP error codes.
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

        switch (httpCode) {
            case 400:
                return BAD_REQUEST;
            case 401:
                return NOT_AUTHENTICATED;
            case 403:
                return NOT_AUTHORIZED;
            case 404:
                return NOT_FOUND;
            case 503:
                return SERVICE_NOT_AVAILABLE;
            default:
                return RUNTIME_ERROR;
        }
    }
}
