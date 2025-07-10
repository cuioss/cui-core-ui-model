/**
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
/**
 * Provides a comprehensive framework for handling service results and errors in a type-safe
 * and user-interface-friendly manner. This package offers an alternative to traditional
 * exception-based error handling, making it particularly suitable for UI applications.
 *
 * <h2>Core Concepts</h2>
 * <p>
 * The package introduces a result wrapper pattern that:
 * <ul>
 *   <li>Encapsulates both success and error states</li>
 *   <li>Provides type-safe access to results</li>
 *   <li>Supports detailed error information</li>
 *   <li>Enables graceful UI error handling</li>
 *   <li>Maintains JSF lifecycle integrity</li>
 * </ul>
 *
 * <h2>Key Components</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.result.ResultObject} - Core wrapper for service results</li>
 *   <li>{@link de.cuioss.uimodel.result.ResultDetail} - Detailed error information</li>
 *   <li>{@link de.cuioss.uimodel.result.ResultState} - Enumeration of possible result states</li>
 *   <li>{@link de.cuioss.uimodel.result.ResultOptional} - Optional-based result handling</li>
 *   <li>{@link de.cuioss.uimodel.result.ResultErrorCodes} - Standardized error codes</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>1. Service Provider Implementation</h3>
 * <pre>
 * public ResultObject&lt;Patient&gt; retrievePatient(SearchParameter param) {
 *     // Create builder with default result for error cases
 *     ResultObject.Builder&lt;Patient&gt; builder = new ResultObject.Builder&lt;&gt;()
 *         .validDefaultResult(new Patient());
 *     
 *     // Attempt to find patient
 *     Patient patient = service.lookupPatient(param);
 *     if (patient == null) {
 *         // Handle not found case without exceptions
 *         return builder
 *             .state(ResultState.ERROR)
 *             .resultDetail(new ResultDetail(
 *                 new DisplayName("Patient not found")))
 *             .errorCode(ResultErrorCodes.NOT_FOUND)
 *             .build();
 *     }
 *     
 *     // Handle successful case
 *     return builder
 *         .result(patient)
 *         .state(ResultState.VALID)
 *         .build();
 * }
 * </pre>
 *
 * <h3>2. UI Layer Integration</h3>
 * <pre>
 * public void handlePatientSearch() {
 *     ResultObject&lt;Patient&gt; result = service.retrievePatient(searchParam);
 *     
 *     if (result.isValid()) {
 *         displayPatient(result.getResult());
 *     } else {
 *         // Handle error with appropriate UI feedback
 *         result.getResultDetail().ifPresent(detail -> 
 *             messageProducer.addError(detail));
 *             
 *         // Handle specific error cases
 *         result.getErrorCode().ifPresent(code -> {
 *             if (code == ResultErrorCodes.NOT_FOUND) {
 *                 showNotFoundMessage();
 *             }
 *         });
 *     }
 * }
 * </pre>
 *
 * <h3>3. Optional-based Handling</h3>
 * <pre>
 * public ResultOptional&lt;Document&gt; findDocument(String id) {
 *     Document doc = repository.findById(id);
 *     if (doc == null) {
 *         return new ResultOptional&lt;&gt;(
 *             null,
 *             ResultState.INFO,
 *             new ResultDetail(new DisplayName("Document not available")));
 *     }
 *     return new ResultOptional&lt;&gt;(doc, ResultState.VALID);
 * }
 * </pre>
 *
 * <h2>Benefits</h2>
 * <ul>
 *   <li><strong>Clean Architecture</strong>
 *     <ul>
 *       <li>Decouples error handling from business logic</li>
 *       <li>Reduces dependency on backend exception types</li>
 *       <li>Promotes consistent error handling patterns</li>
 *     </ul>
 *   </li>
 *   <li><strong>Improved UI Development</strong>
 *     <ul>
 *       <li>Eliminates try-catch blocks in UI code</li>
 *       <li>Provides default objects for graceful degradation</li>
 *       <li>Maintains JSF lifecycle integrity</li>
 *     </ul>
 *   </li>
 *   <li><strong>Enhanced Error Handling</strong>
 *     <ul>
 *       <li>Structured error information</li>
 *       <li>Support for multiple error details</li>
 *       <li>Localization-ready error messages</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Best Practices</h2>
 * <ul>
 *   <li>Always provide meaningful default objects for error cases</li>
 *   <li>Use {@code ResultOptional} for nullable results</li>
 *   <li>Include relevant context in {@code ResultDetail} objects</li>
 *   <li>Leverage standard error codes for consistent handling</li>
 *   <li>Consider i18n requirements when creating error messages</li>
 * </ul>
 *
 * <h2>Integration Points</h2>
 * <ul>
 *   <li>JSF backing beans</li>
 *   <li>Service facades</li>
 *   <li>REST clients</li>
 *   <li>Business services</li>
 *   <li>Validation frameworks</li>
 * </ul>
 *
 * @author Eugen Fischer
 * @see de.cuioss.uimodel.result.ResultObject
 * @see de.cuioss.uimodel.result.ResultDetail
 * @see de.cuioss.uimodel.result.ResultState
 * @since 1.0
 */
package de.cuioss.uimodel.result;
