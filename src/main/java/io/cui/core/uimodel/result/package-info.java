/**
 * Package include generic service result wrapper API.
 * <h2>Generic way to handle service call exceptions</h2>
 * <p>
 * Instead communicate error proposal via Exceptions the idea is to wrap service result objects.
 * This allows to keep UI code without try catch blocks, redundant default model initializations
 * and supports dedicated use-case specific for error handling.
 * This is a extension to global error handling which is designed as last line of defense.
 * </p>
 * <h3>Concept Overview</h3>
 * <p>
 * Any service request which <b>could</b> terminate with exception should be wrapped in
 * {@linkplain io.cui.core.uimodel.result.ResultObject}.
 * RequestResultObject include <b>always</b> a valid result.
 * (Expected) Exceptions which occurs will be included in
 * {@linkplain io.cui.core.uimodel.result.ResultDetail}.
 * Depend on state the client (UI) could dedicated react.
 * </p>
 * <h3>Additional benefit</h3>
 * <ul>
 * <li>unnecessary dependencies (BE Exception classes) solved, (FE need only knowledge of ui-facade)
 * </li>
 * <li>dedicated error handling on ui became easier (no try-catch needed)</li>
 * <li>default objects make the implementation easier (because it's common)</li>
 * <li>errors do not interrupt the JSF live-cycle, which could cause incomplete rendering. (if
 * default object are well designed)</li>
 * </ul>
 *
 * @author Eugen Fischer
 */
package io.cui.core.uimodel.result;
