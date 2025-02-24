/**
 * Provides a comprehensive set of model classes for handling common UI data structures
 * and patterns. This package contains foundational model classes that represent core
 * concepts used throughout the UI layer.
 *
 * <h2>Core Components</h2>
 *
 * <h3>Basic Models</h3>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.Gender} - Enumeration for gender representation</li>
 *   <li>{@link de.cuioss.uimodel.model.ConditionalInteger} - Integer wrapper with conditional state</li>
 *   <li>{@link de.cuioss.uimodel.model.RangeCounter} - Counter with min/max range support</li>
 *   <li>{@link de.cuioss.uimodel.model.TypedSelection} - Generic type-safe selection model</li>
 * </ul>
 *
 * <h3>Code System</h3>
 * <p>The {@code code} sub-package provides a flexible system for handling coded values
 * and types, commonly used in medical and administrative systems:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.code.CodeType} - Interface for code-based types</li>
 *   <li>{@link de.cuioss.uimodel.model.code.GenderCodeType} - Specialized codes for gender</li>
 * </ul>
 *
 * <h3>Concept Keys</h3>
 * <p>The {@code conceptkey} sub-package offers a framework for managing hierarchical
 * concept keys and categories:
 * <ul>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.ConceptKeyType} - Type system for concepts</li>
 *   <li>{@link de.cuioss.uimodel.model.conceptkey.ConceptCategory} - Category management</li>
 * </ul>
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>Working with Codes</h3>
 * <pre>
 * // Create and use a code type
 * CodeType genderCode = new CodeTypeImpl("F", "Female");
 * String display = genderCode.getResolved(); // Returns "Female"
 *
 * // Use the gender enumeration
 * Gender gender = Gender.FEMALE;
 * String key = gender.getKey(); // Returns localization key
 * </pre>
 *
 * <h3>Range Operations</h3>
 * <pre>
 * // Create a bounded counter
 * RangeCounter counter = new BaseRangeCounter(0, 100);
 * counter.increment();
 * counter.setValue(50);
 * </pre>
 *
 * <h3>Selections</h3>
 * <pre>
 * // Create a type-safe selection
 * TypedSelection&lt;String&gt; selection = new TypedSelection&lt;&gt;();
 * selection.setItems(Arrays.asList("A", "B", "C"));
 * selection.setSelectedItem("B");
 * </pre>
 *
 * @author Sven Haag
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.model;
