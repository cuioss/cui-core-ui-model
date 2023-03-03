package de.cuioss.uimodel.service;

/**
 * An optional service is a service that may be accessible or not. This usually defined by the
 * concrete configuration or at runtime, e.g. if a remote service is not available
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
     * Shortcut for checking whether the service is available for the current call context.
     *
     * @return boolean indicating that the service is up and running and accessible for the current
     *         logged in user
     */
    default boolean isServiceAvailable() {
        return ServiceState.ACTIVE.equals(getServiceState());
    }
}
