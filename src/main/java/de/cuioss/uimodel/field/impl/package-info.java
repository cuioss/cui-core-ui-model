/**
 * Provides concrete implementations for the dynamic field framework defined in
 * {@link de.cuioss.uimodel.field}. This package contains base classes and
 * type-specific implementations that form the core functionality of the field system.
 *
 * <h2>Base Implementations</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseDynamicField} - Core field functionality</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseTracedDynamicField} - Change tracking support</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.BaseLabeledDynamicField} - UI labeling support</li>
 * </ul>
 *
 * <h2>Type-Specific Fields</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.BooleanEditableField} - Boolean values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.StringEditableField} - String values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.IntegerEditableField} - Integer values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.LongEditableField} - Long values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.FloatEditableField} - Float values</li>
 *   <li>{@link de.cuioss.uimodel.field.impl.DoubleEditableField} - Double values</li>
 * </ul>
 *
 * <h2>Special Implementations</h2>
 * <ul>
 *   <li>{@link de.cuioss.uimodel.field.impl.UnlockableTracedDynamicFieldImpl} - Protected fields</li>
 * </ul>
 *
 * @author Oliver Wolff
 * @since 1.0
 */
package de.cuioss.uimodel.field.impl;
