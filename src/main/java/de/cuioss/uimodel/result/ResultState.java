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
