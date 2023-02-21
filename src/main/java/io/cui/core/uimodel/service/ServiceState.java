package io.cui.core.uimodel.service;

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
     * Indicates that the service is not configured at all or the configuration is incomplete or
     * invalid.
     */
    NOT_CONFIGURED,

    /**
     * Indicates that the service is {@link #ACTIVE} but can can not be used by the currently logged
     * in user
     */
    NOT_AVAILABLE_FOR_USER
}
