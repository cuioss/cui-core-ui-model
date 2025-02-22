/**
 * Provides a comprehensive framework for handling code-based data structures and their
 * transformations across different system components. This package is designed to
 * standardize the way codes (such as status codes, type codes, or reference codes)
 * are represented, compared, and transmitted throughout the application.
 *
 * <p>Key Components:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeType} - Core interface defining the contract for code types</li>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeTypeImpl} - Standard implementation of the CodeType interface</li>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeTypeComparator} - Utility for comparing code types</li>
 *   <li>{@link de.cuioss.uimodel.model.code.GenderCodeType} - Specialized implementation for gender codes</li>
 * </ul>
 *
 * <p>Features:
 * <ul>
 *   <li>Type-safe code handling</li>
 *   <li>Consistent comparison and sorting</li>
 *   <li>Immutable implementations</li>
 *   <li>Serialization support</li>
 *   <li>Localization capabilities</li>
 * </ul>
 *
 * <p>Usage Example:
 * <pre>
 * // Creating a new code type
 * CodeType statusCode = new CodeTypeImpl("ACTIVE", "Active Status");
 *
 * // Using the comparator
 * List&lt;CodeType&gt; codes = new ArrayList&lt;&gt;();
 * codes.sort(new CodeTypeComparator());
 * </pre>
 *
 * @author Sven Haag
 * @since 1.0
 */
package de.cuioss.uimodel.model.code;
