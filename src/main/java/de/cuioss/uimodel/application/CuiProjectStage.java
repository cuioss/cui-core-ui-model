package de.cuioss.uimodel.application;

import java.io.Serializable;

/**
 * Generalization for JSFs / DeltaSpikes Project-Stage
 *
 * @author Oliver Wolff
 */
public interface CuiProjectStage extends Serializable {

    /**
     * @return true if project stage is 'development'.
     */
    boolean isDevelopment();

    /**
     * @return true if project stage is 'test'.
     */
    boolean isTest();

    /**
     * @return true if project stage is 'configuration'.
     */
    boolean isConfiguration();

    /**
     * @return true if project stage is 'production' or unknown.
     */
    boolean isProduction();
}
