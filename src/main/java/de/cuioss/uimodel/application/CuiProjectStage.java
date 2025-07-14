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
package de.cuioss.uimodel.application;

import java.io.Serializable;

/**
 * Defines a common interface for managing application project stages, abstracting the
 * concept from specific implementations like JSF's or DeltaSpike's ProjectStage.
 * Project stages are used to configure application behavior based on the deployment
 * environment.
 *
 * <p>Features:
 * <ul>
 *   <li>Supports three standard stages: Development, Test, and Production</li>
 *   <li>Provides clear stage identification methods</li>
 *   <li>Treats unknown stages as Production for safety</li>
 *   <li>Implements {@link Serializable} for state management</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Check current stage and adjust behavior
 * if (projectStage.isDevelopment()) {
 *     // Enable detailed logging
 *     logger.setLevel(Level.DEBUG);
 *     // Show development tools
 *     devTools.setVisible(true);
 * }
 *
 * // Configure caching based on stage
 * if (projectStage.isProduction()) {
 *     // Enable aggressive caching
 *     cache.setTimeToLive(Duration.ofHours(24));
 * } else {
 *     // Disable caching for development/test
 *     cache.disable();
 * }
 * </pre>
 *
 * <p>Implementation Notes:
 * <ul>
 *   <li>Implementations should ensure exactly one stage is active at a time</li>
 *   <li>The production stage should be the default for undefined cases</li>
 *   <li>Stage transitions should not be allowed during runtime</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
public interface CuiProjectStage extends Serializable {

    /**
     * Checks if the application is running in development mode.
     * Development mode typically enables additional debugging features,
     * detailed logging, and development tools.
     *
     * @return {@code true} if the current stage is 'development',
     *         {@code false} otherwise
     */
    boolean isDevelopment();

    /**
     * Checks if the application is running in test mode.
     * Test mode is typically used for automated testing, integration
     * testing, or staging environments.
     *
     * @return {@code true} if the current stage is 'test',
     *         {@code false} otherwise
     */
    boolean isTest();

    /**
     * Checks if the application is running in production mode.
     * Production mode is the default stage and should be used for
     * live deployments. Unknown stages are treated as production
     * for security reasons.
     *
     * @return {@code true} if the current stage is 'production'
     *         or if the stage is unknown, {@code false} otherwise
     */
    boolean isProduction();
}
