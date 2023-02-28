package io.cui.core.uimodel.model;

import java.io.Serializable;

/**
 * Helper method utilized for accessing Project Stage information in a convenient way. It can be
 * exposed Application Scoped bean, because the Project stage does not change between startup.
 *
 * @author Oliver Wolff
 */
public interface ProjectStage extends Serializable {

    /**
     * Bean name for looking up instances.
     */
    String BEAN_NAME = "cuiProjectStage";

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
